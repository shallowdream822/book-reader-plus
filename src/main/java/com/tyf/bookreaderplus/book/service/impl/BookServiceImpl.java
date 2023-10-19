package com.tyf.bookreaderplus.book.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tyf.bookreaderplus.auth.dao.BrBookUserMapper;
import com.tyf.bookreaderplus.author.dao.BrAuthorMapper;
import com.tyf.bookreaderplus.author.entity.BrAuthor;
import com.tyf.bookreaderplus.book.constant.BookConstants;
import com.tyf.bookreaderplus.book.dao.BrBookMapper;
import com.tyf.bookreaderplus.book.dao.BrCategoryMapper;
import com.tyf.bookreaderplus.book.dto.BookCategoryDto;
import com.tyf.bookreaderplus.book.dto.BookDto;
import com.tyf.bookreaderplus.book.dto.BookInfoDto;
import com.tyf.bookreaderplus.book.entity.BrBook;
import com.tyf.bookreaderplus.book.entity.BrCategory;
import com.tyf.bookreaderplus.book.service.BookService;
import com.tyf.bookreaderplus.common.constant.RedisConstants;
import com.tyf.bookreaderplus.common.constant.StatusCodeEnum;
import com.tyf.bookreaderplus.common.exception.BrException;
import com.tyf.bookreaderplus.common.utils.RedisUtil;
import com.tyf.bookreaderplus.common.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/4/2 9:52
 */

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private BrCategoryMapper categoryMapper;

    @Autowired
    private BrBookMapper bookMapper;

    @Autowired
    private BrAuthorMapper authorMapper;

    @Autowired
    private BrBookUserMapper bookUserMapper;

    /**
     * 查看小说分类表
     * @return
     */
    @Override
    public List<BookCategoryDto> listCategory() {
        List<BookCategoryDto> bookCategoryList;
        //检查缓存是否有内容
        if (redisUtil.lSize(RedisConstants.BOOK_CATEGORY_LIST_CACHE_KEY) == 0) {
            //缓存没有内容，从数据库中查询
            bookCategoryList =categoryMapper.selectList(new LambdaQueryWrapper<BrCategory>().eq(BrCategory::getDeleted,0))
                    .stream().map(v ->{
                        BookCategoryDto bookCategoryDto = BookCategoryDto.builder()
                                .id(v.getId())
                                .name(v.getCategoryName())
                                .build();
                        redisUtil.lPush(RedisConstants.BOOK_CATEGORY_LIST_CACHE_KEY,bookCategoryDto,RedisConstants.COMMON_CACHE_TIME);
                        return bookCategoryDto;
                    }).toList();
        }else{
            //缓存有内容，从缓存中查询
            bookCategoryList=redisUtil.lRange(RedisConstants.BOOK_CATEGORY_LIST_CACHE_KEY,0,-1)
                    .stream()
                    .map(v-> BeanUtil.toBean(v,BookCategoryDto.class))
                    .toList();
        }
        return bookCategoryList;
    }

    /**
     * 根据id获取小说信息
     * @param bookId
     * @return
     */
    @Override
    public BookInfoDto getBookById(Long bookId) {
        BookInfoDto bookInfoDto;
        Object info = redisUtil.hGet(RedisConstants.BOOK_INFO_CACHE_KEY, String.valueOf(bookId));
        if (Objects.nonNull(info)){
            log.debug("走缓存");
            bookInfoDto = BeanUtil.toBean(info,BookInfoDto.class);
            if(bookInfoDto.getId() == null){
                throw new BrException(StatusCodeEnum.FAIL.getCode(),"该书籍不存在");
            }
        }else{
            //缓存未命中
            log.debug("走数据库");
            LambdaQueryWrapper<BrBook> wrapper = new LambdaQueryWrapper<BrBook>().eq(BrBook::getId, bookId).eq(BrBook::getDeleted, 0);
            BrBook bookInfo = bookMapper.selectOne(wrapper);
            if (Objects.isNull(bookInfo)){
                redisUtil.hSet(RedisConstants.BOOK_INFO_CACHE_KEY,String.valueOf(bookId),new BookInfoDto(),RedisConstants.COMMON_CACHE_TIME);
                throw new BrException(StatusCodeEnum.FAIL.getCode(),"该书籍不存在");
            }
            bookInfoDto = BookInfoDto.builder()
                    .id(bookInfo.getId())
                    .categoryId(bookInfo.getCategoryId())
                    .categoryName(bookInfo.getCategoryName())
                    .bookDesc(bookInfo.getBookDesc())
                    .bookImg(bookInfo.getBookImg())
                    .bookName(bookInfo.getBookName())
                    .authorId(bookInfo.getAuthorId())
                    .authorName(authorMapper.selectOne(new LambdaQueryWrapper<BrAuthor>().eq(BrAuthor::getId,bookInfo.getAuthorId())).getAuthorName())
                    .bookLastedChapter(bookInfo.getBookLatestChapter())
                    .publisher(bookInfo.getBookPublish())
                    .publishYear(bookInfo.getBookYear())
                    .bookHasRead(bookInfo.getBookHasread())
                    .bookStars(bookInfo.getBookStars())
                    .bookPrice(bookInfo.getBookPrice()).build();
            redisUtil.hSet(RedisConstants.BOOK_INFO_CACHE_KEY,String.valueOf(bookId),bookInfoDto,RedisConstants.COMMON_CACHE_TIME);
        }
        return bookInfoDto;
    }

    /**
     * 增加小说阅读量
     * @param bookId
     * @return
     */
    @Override
    public void addVisitCount(Long bookId) {
        //首先查询缓存中是否有访问信息，如果有，则更新缓存中的信息，访问量+1，使用redis提供的原子操作
        Object count = redisUtil.hGet(RedisConstants.BOOK_VISIT_COUNT, String.valueOf(bookId));
        if(Objects.nonNull(count)){
            //缓存中有数据,自增
            redisUtil.hIncr(RedisConstants.BOOK_VISIT_COUNT,String.valueOf(bookId),1L);
            return;
            //异步更新数据库
        }
        //缓存中没有数据，去数据库中读,并放入缓存中
        //加分布式锁
        RLock lock = redissonClient.getLock(RedisConstants.REDISSON_LOCK_VISIT + bookId);
        try {
            lock.lock();
            int bookHasread = bookMapper.selectById(bookId).getBookHasread();
            redisUtil.hSet(RedisConstants.BOOK_VISIT_COUNT,String.valueOf(bookId),bookHasread+1,RedisConstants.COMMON_CACHE_TIME);
            log.debug("访问数{}",bookHasread+1);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }




    /**
     * 小说推荐列表，推荐最近更新的同类别小说
     * @param bookId
     * @return
     */
    @Override
    public List<BookDto> listRecBooks(Long bookId) throws NoSuchAlgorithmException {
        //获得类别id
        Long categoryId = getBookById(bookId).getCategoryId();
        //获得该类最近更新书目id
        List<Long> lastUpdateList ;
        if(redisUtil.lSize(RedisConstants.CATEGORY_LAST_UPDATE_CACHE_KEY)!=0){
            lastUpdateList = redisUtil.lRange(RedisConstants.CATEGORY_LAST_UPDATE_CACHE_KEY, 0, -1).stream().map(v->(Long)v).toList();
        }else{
            lastUpdateList = bookMapper.selectList(new LambdaQueryWrapper<BrBook>()
                    .eq(BrBook::getCategoryId, categoryId)
                    .eq(BrBook::getDeleted, 0)
                    .orderByDesc(BrBook::getUpdateTime)
                    .last("limit 500")).stream().map(v->{
                redisUtil.lPush(RedisConstants.CATEGORY_LAST_UPDATE_CACHE_KEY,v.getId(),RedisConstants.COMMON_CACHE_TIME);
                return v.getId();
            }).toList();
        }
        //封装结果
        List<BookDto> bookDtoList = new ArrayList<>();
        Random rand = SecureRandom.getInstanceStrong();
        List<Integer> recIdIndexList = new ArrayList<>();
        int count = 0;
        while(count< BookConstants.REC_BOOK_COUNT){
            int index = rand.nextInt(lastUpdateList.size());
            if(!recIdIndexList.contains(index)){
                recIdIndexList.add(index);
                bookId = lastUpdateList.get(index);
                BookDto bookDto = getBookDtoById(bookId);
                if(Objects.nonNull(bookDto)){
                    bookDtoList.add(bookDto);
                }
                count++;
            }
        }
        return bookDtoList;
    }

    public BookDto getBookDtoById(Long bookId){
        return bookMapper.getBookDtoById(bookId);
    }
    @Override
    public boolean checkBookPurchase(Long bookId) {
        return UserUtils.getLoginUser().getBookIdList().contains(bookId);
    }
}
