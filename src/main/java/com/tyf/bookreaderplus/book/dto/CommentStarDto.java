package com.tyf.bookreaderplus.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/9 17:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentStarDto {

    private Long commentStars;

    private String message;
}
