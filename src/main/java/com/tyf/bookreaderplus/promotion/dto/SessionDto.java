package com.tyf.bookreaderplus.promotion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/11/20 21:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionDto {

    private Long sessionId;

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    /**状态： 0-未开始，1-进行中，2-已结束*/
    private Integer status;

    private List<SessionBookDto> sessionBookList;
}
