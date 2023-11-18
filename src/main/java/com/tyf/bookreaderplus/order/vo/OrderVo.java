package com.tyf.bookreader.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/2 21:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderVo {
    private List<OrderDetailsVo> orderDetailsVoList;
}
