package com.tyf.bookreaderplus.book.component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.tyf.bookreaderplus.book.dao.BrCommentMapper;
import com.tyf.bookreaderplus.book.dao.BrUserStarCommentMapper;
import com.tyf.bookreaderplus.book.entity.BrComment;
import com.tyf.bookreaderplus.book.entity.BrUserStarComment;
import com.tyf.bookreaderplus.common.constant.RedisConstants;
import com.tyf.bookreaderplus.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/10 16:19
 */
@Component
@Slf4j
public class CommenttTask {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BrCommentMapper commentMapper;

    @Autowired
    private BrUserStarCommentMapper userStarCommentMapper;

    @Scheduled(cron = "* * 1 * * *")
    @Transactional
    public void saveCommentStar(){
        log.debug("定时任务执行时间:{}", LocalDateTime.now());
        Map<String, Object> commentStarNumMap = redisUtil.hGetAll(RedisConstants.COMMENT_STAR_NUM_CACHE_KEY);
        commentStarNumMap.forEach((commentId,commentStars)->{
            System.out.println(commentId+":"+commentStars);
            Integer stars = (Integer) commentStars;
            BrComment comment = BrComment.builder()
                    .id(Long.valueOf(commentId))
                    .commentStars(stars)
                    .build();
            commentMapper.updateById(comment);
        });
        Map<String, Object> stringObjectMap = redisUtil.hGetAll(RedisConstants.COMMENT_STAR_RELATION_CACHE_KEY);
        stringObjectMap.forEach((key,value)->{
            int flag = key.indexOf(':');
            Integer status = (Integer) value;
            Long commentId = Long.valueOf(key.substring(0, flag));
            Long userId = Long.valueOf(key.substring(flag+1));
            BrUserStarComment existRecord = userStarCommentMapper.selectOne(new LambdaQueryWrapper<BrUserStarComment>()
                    .eq(BrUserStarComment::getCommentId, commentId)
                    .eq(BrUserStarComment::getUserId, userId));
            BrUserStarComment userStarComment = BrUserStarComment.builder().commentId(commentId).userId(userId).status(status).build();
            if (Objects.isNull(existRecord)){
                userStarCommentMapper.insert(userStarComment);
            }else{
                userStarComment.setId(existRecord.getId());
                userStarCommentMapper.updateById(userStarComment);
            }
        });

    }
}
