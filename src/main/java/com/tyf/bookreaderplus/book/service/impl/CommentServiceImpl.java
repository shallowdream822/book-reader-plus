package com.tyf.bookreaderplus.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyf.bookreaderplus.auth.dao.BrUserMapper;
import com.tyf.bookreaderplus.auth.entity.BrUser;
import com.tyf.bookreaderplus.book.dao.BrCommentMapper;
import com.tyf.bookreaderplus.book.dao.BrUserStarCommentMapper;
import com.tyf.bookreaderplus.book.dto.CommentDTO;
import com.tyf.bookreaderplus.book.dto.CommentStarDto;
import com.tyf.bookreaderplus.book.entity.BrComment;
import com.tyf.bookreaderplus.book.entity.BrUserStarComment;
import com.tyf.bookreaderplus.book.service.CommentService;
import com.tyf.bookreaderplus.book.vo.CommentPageVo;
import com.tyf.bookreaderplus.common.constant.RedisConstants;
import com.tyf.bookreaderplus.common.dto.PageRespDto;
import com.tyf.bookreaderplus.common.utils.RedisUtil;
import com.tyf.bookreaderplus.common.utils.UserUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Description 评论业务实现类
 * @Author shallow
 * @Date 2023/4/3 16:58
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private BrCommentMapper commentMapper;

    @Autowired
    private BrUserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private BrUserStarCommentMapper userStarCommentMapper;



    @Override
    public PageRespDto<CommentDTO> getCommentList(CommentPageVo commentPageVo) {
        //查询评论总量
        Long bookId = commentPageVo.getBookId();
        int sort = commentPageVo.getSort();
        long parentId = commentPageVo.getParentId()==null?0:commentPageVo.getParentId();
        int pageNum = commentPageVo.getPageNum();
        int pageSize = commentPageVo.getPageSize();
        Page<BrComment> commentList;
        Page<BrComment> page = new Page<>(pageNum, pageSize);
        if (sort == 0){
            commentList=commentMapper.selectPage(page,new LambdaQueryWrapper<BrComment>()
                     .eq(BrComment::getBookId, bookId)
                     .eq(BrComment::getParentId,parentId)
                     .orderByDesc(BrComment::getCommentStars));
        }else{
            commentList = commentMapper.selectPage(page,new LambdaQueryWrapper<BrComment>()
                    .eq(BrComment::getBookId, bookId)
                    .eq(BrComment::getParentId,parentId)
                    .orderByDesc(BrComment::getCreateTime));
        }

        //
        List<CommentDTO> commentDTOList = getCommentDtoList(commentList.getRecords());
        return new PageRespDto<>(pageNum, pageSize, commentList.getTotal(), commentDTOList);
    }



    @Override
    public List<CommentDTO> getHotCommentsById(Long bookId) {
        Integer count = commentMapper.selectCount(new LambdaQueryWrapper<BrComment>()
                .eq(BrComment::getBookId, bookId)
                .eq(BrComment::getParentId,0));
        if (count == 0){
            return null;
        }
        List<BrComment>  commentList = commentMapper.selectList(new LambdaQueryWrapper<BrComment>()
                    .eq(BrComment::getBookId, bookId)
                    .eq(BrComment::getParentId,0)
                    .orderByDesc(BrComment::getCommentStars)
                    .last("limit 5"));

        return getCommentDtoList(commentList);
    }

    private List<CommentDTO> getCommentDtoList(List<BrComment> commentList) {
        List<CommentDTO> commentDTOList = commentList.stream().map(v -> {
            BrUser user = userMapper.selectById(v.getUserId());
            Integer commentStars = getStarsById(v.getId());
            return CommentDTO.builder()
                    .id(v.getId())
                    .userId(v.getUserId())
                    .userName(user.getUserName())
                    .userImg(user.getUserImg())
                    .createTime(v.getCreateTime())
                    .commentContent(v.getCommentContent())
                    .parentId(v.getParentId())
                    .commentStars(commentStars).build();

        }).toList();
        return commentDTOList;
    }

    private Integer getStarsById(Long commentId){
        Integer starNum = (Integer) redisUtil.hGet(RedisConstants.COMMENT_STAR_NUM_CACHE_KEY, String.valueOf(commentId));
        if (Objects.nonNull(starNum)) {
            return starNum;
        }
        BrComment comment = commentMapper.selectById(commentId);
        redisUtil.hSet(RedisConstants.COMMENT_STAR_NUM_CACHE_KEY,String.valueOf(commentId),comment.getCommentStars(),RedisConstants.COMMON_CACHE_TIME);
        return comment.getCommentStars();
    }

    @Override
    public void optCommentStar(Long commentId) {
        optComment(commentId, UserUtils.getLoginUser().getUser().getId());
    }

    public void optComment(Long commentId, Long userId){
        RLock lock = redissonClient.getLock(RedisConstants.REDISSON_LOCK_COMMENT + userId);
        CommentStarDto commentStarDto = new CommentStarDto();
        try {
            lock.lock();
            //判断是否点过赞
            boolean hasStared = hasStared(commentId, userId);
            //已点赞则取消点赞，点赞数-1
            if(hasStared){
                cancelStar(commentId,userId);
                commentStarDto.setMessage("取消点赞成功");
            }else{
                addStar(commentId,userId);
                commentStarDto.setMessage("点赞成功");
            }
            Long stars = Long.valueOf(redisUtil.hGet(RedisConstants.COMMENT_STAR_NUM_CACHE_KEY, String.valueOf(commentId)).toString());
            commentStarDto.setCommentStars(stars);
        } finally {
            lock.unlock();
        }
    }

    protected void cancelStar(Long commentId, Long userId) {
        String relationKey = RedisConstants.COMMENT_STAR_RELATION_CACHE_KEY;
        Object starNum = redisUtil.hGet(RedisConstants.COMMENT_STAR_NUM_CACHE_KEY, String.valueOf(commentId));
        if (Objects.nonNull(starNum)){
            redisUtil.hDecr(RedisConstants.COMMENT_STAR_NUM_CACHE_KEY,String.valueOf(commentId),1L);
            redisUtil.hSet(relationKey,commentId +":"+ userId,1,RedisConstants.COMMON_CACHE_TIME);
            return;
        }
        Integer commentStars = commentMapper.selectById(commentId).getCommentStars();
        redisUtil.hSet(RedisConstants.COMMENT_STAR_NUM_CACHE_KEY,String.valueOf(commentId),commentStars,RedisConstants.COMMON_CACHE_TIME);
        redisUtil.hDecr(RedisConstants.COMMENT_STAR_NUM_CACHE_KEY,String.valueOf(commentId),1L);
        redisUtil.hSet(relationKey, commentId +":"+ userId,1,RedisConstants.COMMON_CACHE_TIME);


    }

    protected void addStar(Long commentId, Long userId){
        String relationKey = RedisConstants.COMMENT_STAR_RELATION_CACHE_KEY;
        Object starNum = redisUtil.hGet(RedisConstants.COMMENT_STAR_NUM_CACHE_KEY, String.valueOf(commentId));
        if (Objects.nonNull(starNum)){
            redisUtil.hIncr(RedisConstants.COMMENT_STAR_NUM_CACHE_KEY,String.valueOf(commentId),1L);
            redisUtil.hSet(relationKey,commentId +":"+ userId,0,RedisConstants.COMMON_CACHE_TIME);
            return;
        }
        Integer commentStars = commentMapper.selectById(commentId).getCommentStars();
        redisUtil.hSet(RedisConstants.COMMENT_STAR_NUM_CACHE_KEY,String.valueOf(commentId),commentStars,RedisConstants.COMMON_CACHE_TIME);
        redisUtil.hIncr(RedisConstants.COMMENT_STAR_NUM_CACHE_KEY,String.valueOf(commentId),1L);
        redisUtil.hSet(relationKey,commentId +":"+ userId,0,RedisConstants.COMMON_CACHE_TIME);
    }

    protected boolean hasStared(Long commentId,Long userId){
        //判断是否点过赞,redis中value为0为已点赞，value为1为未点赞
        String relationKey = RedisConstants.COMMENT_STAR_RELATION_CACHE_KEY;
        Integer flag = (Integer)redisUtil.hGet(relationKey, commentId+":"+userId);
        if(Objects.nonNull(flag)){
            return flag.equals(0);
        }
        //redis中没有数据，去数据库中查看关系
        BrUserStarComment userStarComment = userStarCommentMapper.selectOne(new LambdaQueryWrapper<BrUserStarComment>()
                .eq(BrUserStarComment::getCommentId, commentId)
                .eq(BrUserStarComment::getUserId, userId));
        if (Objects.isNull(userStarComment)) {
            //数据库没有数据，说明未点赞
            redisUtil.hSet(relationKey,commentId +":"+ userId,1,RedisConstants.COMMON_CACHE_TIME);
            return false;
        }
        //得到数据库数据，将更新后的数据放入缓存中
        Integer status = userStarComment.getStatus();
        redisUtil.hSet(relationKey,commentId +":"+ userId,status,RedisConstants.COMMON_CACHE_TIME);
        return status.equals(0);
    }
}
