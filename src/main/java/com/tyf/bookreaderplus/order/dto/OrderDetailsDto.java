package com.tyf.bookreaderplus.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 订单信息dto类
 * @Author shallow
 * @Date 2023/5/4 15:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsDto {

    private Long bookId;
    private String bookName;

    private Double bookPrice;

}
