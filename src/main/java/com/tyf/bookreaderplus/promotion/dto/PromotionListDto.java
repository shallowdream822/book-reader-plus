package com.tyf.bookreaderplus.promotion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/11/22 16:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionListDto {
    List<PromotionDto> promotionList;
}
