package com.tyf.bookreaderplus.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 小说分类 dto
 * @Author shallow
 * @Date 2023/4/2 9:57
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookCategoryDto {

    /**
     * 类别id
     */
    private Long id;

    /**
     * 类别名
     */
    private String name;
}
