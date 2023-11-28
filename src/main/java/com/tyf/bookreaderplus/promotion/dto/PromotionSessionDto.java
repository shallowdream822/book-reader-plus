package com.tyf.bookreaderplus.promotion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/11/20 21:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionSessionDto {

    private Long promotionId;

    private String promotionTitle;

    private Date startDate;

    private Date endDate;

    private Integer status;
    //活动详情页信息
    List<SessionDto> sessionList;
}
