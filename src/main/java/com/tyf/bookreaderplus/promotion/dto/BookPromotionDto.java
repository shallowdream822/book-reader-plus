package com.tyf.bookreaderplus.promotion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author shallow
 * @Date: 2023/11/23/ 14:57
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookPromotionDto {

    private Long promotionId;

    private String promotionName;

    private String promotionImg;

    private Long bookId;

    private Long bookPrice;
}
