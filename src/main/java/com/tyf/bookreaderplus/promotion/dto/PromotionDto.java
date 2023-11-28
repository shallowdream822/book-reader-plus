package com.tyf.bookreaderplus.promotion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/11/20 21:22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionDto {
    private Long id;

    private String title;

    private Date startDate;

    private Date endDate;

    private Integer status;

}
