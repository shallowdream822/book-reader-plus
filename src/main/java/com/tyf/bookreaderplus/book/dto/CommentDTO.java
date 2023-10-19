package com.tyf.bookreaderplus.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description 评论dto
 * @Author shallow
 * @Date 2023/2/22 12:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO implements Serializable {
    /**
     * 评论id
     */
    private Long id;

    /**
     * 评论用户id
     */
    private Long userId;

    /**
     * 评论用户名称
     */
    private String userName;
    private String userImg;

    @JsonFormat(pattern = "yyyy/MM/dd HH:dd")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) // 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class) // 序列化
    private LocalDateTime createTime;

    private Long parentId;
    private String commentContent;
    private Integer commentStars;

}
