package com.tyf.bookreaderplus.promotion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/11/20 21:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionBookDto {

    private Long sessionId;

    private Long bookId;

    private String bookImg;

    private Double bookPrice;

    private String bookName;

    private Integer stock;
}
