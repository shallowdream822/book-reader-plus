package com.tyf.bookreaderplus.promotion.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author shallow
 * @Date: 2023/11/28/ 17:03
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeckillVo {

    private Long sessionId;

    private Long promotionId;

    private Long bookId;

    private Long userId;

    private Double bookPrice;

    private String bookName;


}
