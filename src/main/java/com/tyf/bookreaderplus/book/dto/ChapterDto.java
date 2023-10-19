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
 * @Description TODO
 * @Author shallow
 * @Date 2023/4/2 14:33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChapterDto implements Serializable {

    /**
     * 章节id
     */
    private Long id;

    /**
     * 小说id
     */
    private Long bookId;

    /**
     * 章节号
     */
    private Long chapterNum;

    /**
     * 章节名
     */
    private String chapterName;

    /**
     * 章节更新时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:dd")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) // 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class) // 序列化
    private LocalDateTime chapterUpdateTime;
}
