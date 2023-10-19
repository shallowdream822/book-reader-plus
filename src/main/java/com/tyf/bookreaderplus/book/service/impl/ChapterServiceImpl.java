package com.tyf.bookreaderplus.book.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tyf.bookreaderplus.book.constant.BookConstants;
import com.tyf.bookreaderplus.book.dao.BrChapterMapper;
import com.tyf.bookreaderplus.book.dao.BrContentMapper;
import com.tyf.bookreaderplus.book.dto.BookInfoDto;
import com.tyf.bookreaderplus.book.dto.ChapterDto;
import com.tyf.bookreaderplus.book.dto.ContentDto;
import com.tyf.bookreaderplus.book.dto.LastChapterInfoDto;
import com.tyf.bookreaderplus.book.entity.BrChapter;
import com.tyf.bookreaderplus.book.entity.BrContent;
import com.tyf.bookreaderplus.book.service.BookService;
import com.tyf.bookreaderplus.book.service.ChapterService;
import com.tyf.bookreaderplus.common.constant.CommonConstants;
import com.tyf.bookreaderplus.common.constant.RedisConstants;
import com.tyf.bookreaderplus.common.exception.BrException;
import com.tyf.bookreaderplus.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/10/19 13:24
 */
@Service
@Slf4j
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private BookService bookService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private BrChapterMapper chapterMapper;

    @Autowired
    private BrContentMapper contentMapper;
    /**
     * 获得某书最新章节信息
     * @param bookId
     * @return
     */
    @Override
    public LastChapterInfoDto getLastChapterAbout(Long bookId) {
        //查询小说信息
        BookInfoDto bookInfoDto = bookService.getBookById(bookId);
        //查询最新章节信息
        Long chapterId = bookInfoDto.getBookLastedChapter();
        ChapterDto chapterDto = getChapterById(chapterId);

        //查询章节内容，并截取前30字作为摘要
        ContentDto contentDto = getContentByChapterId(chapterId);
        //查询章节总数
        Integer total = chapterMapper.selectCount(new LambdaQueryWrapper<BrChapter>()
                .eq(BrChapter::getBookId, bookId)
                .eq(BrChapter::getDeleted, 0));
        return LastChapterInfoDto.builder()
                .chapterDto(chapterDto)
                .chapterTotal(total.longValue())
                .contentSummary(contentDto.getContent().substring(0,Math.min(BookConstants.DESC_LENGTH,contentDto.getContent().length()))).build();
    }


    /**
     * 获得本书章节列表
     * @param bookId
     * @return
     */
    @Override
    public List<ChapterDto> listChapters(Long bookId) {
        List<BrChapter> chapterList = chapterMapper.selectList((new LambdaQueryWrapper<BrChapter>())
                .eq(BrChapter::getBookId, bookId)
                .eq(BrChapter::getDeleted, 0)
                .orderByAsc(BrChapter::getChapterNum));
        return chapterList.stream().map(
                v-> ChapterDto.builder()
                        .id(v.getId())
                        .bookId(v.getBookId())
                        .chapterNum(v.getChapterNum())
                        .chapterName(v.getChapterName())
                        .chapterUpdateTime(v.getUpdateTime()).build()).toList();
    }

    private ChapterDto getChapterById(Long chapterId){
        ChapterDto chapterDto;
        Object cacheChapter = redisUtil.hGet(RedisConstants.CHAPTER_INFO_CACHE_KEY, String.valueOf(chapterId));
        if(!Objects.isNull(cacheChapter)){
            log.info("走缓存");
            chapterDto = BeanUtil.toBean(cacheChapter,ChapterDto.class);
        }else {
            log.info("走数据库");
            BrChapter chapter = chapterMapper.selectById(chapterId);
            if (Objects.isNull(chapter)){
                throw new BrException("该章节不存在");
            }
            chapterDto = ChapterDto.builder()
                    .id(chapter.getId())
                    .bookId(chapter.getBookId())
                    .chapterNum(chapter.getChapterNum())
                    .chapterName(chapter.getChapterName())
                    .chapterUpdateTime(chapter.getUpdateTime()).build();
            redisUtil.hSet(RedisConstants.CHAPTER_INFO_CACHE_KEY,String.valueOf(chapterId),chapterDto,RedisConstants.COMMON_CACHE_TIME);
        }
        return chapterDto;
    }

    /**
     * 获取本章内容
     * @param chapterId
     * @return
     */
    @Override
    public ContentDto getContentByChapterId(Long chapterId) {
        //查询本章节信息
        ChapterDto chapterDto = getChapterById(chapterId);
        //获取本章内容
        BrContent chapterContent = contentMapper.selectOne(new LambdaQueryWrapper<BrContent>().eq(BrContent::getChapterId, chapterId));
        String content;
        if(Objects.isNull(chapterContent)){
            content = BookConstants.NO_CONTENT;
        }else{
            content = chapterContent.getContent();
        }
        return ContentDto.builder()
                .chapterNum(chapterDto.getChapterNum())
                .content(content)
                .chapterName(chapterDto.getChapterName())
                .build();
    }

    @Override
    public ContentDto getNeighborChapterId(Long chapterId, Boolean type) {
        Long targetChapterId = chapterId;
        if(Objects.isNull(type)||type){
            targetChapterId=targetChapterId+1;
        }else{
            targetChapterId=targetChapterId-1;
        }

        ContentDto contentDto = getContentByChapterId(targetChapterId);
        return contentDto;
    }


}
