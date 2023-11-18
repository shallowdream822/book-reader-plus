package com.tyf.bookreaderplus.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 订单dto类
 * @Author shallow
 * @Date 2023/5/4 15:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private Long orderNum;

    private Double orderPrice;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime payTime;

    private List<OrderDetailsDto> orderDetailsList;
}
