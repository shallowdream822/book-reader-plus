package com.tyf.bookreaderplus.order.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/2 21:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsVo {

    private Long bookId;

    private String bookName;

    private Double bookPrice;
}
