package com.tyf.bookreaderplus.common.component;

import lombok.Data;

/**
 * @Description 分页请求对象
 * @Author shallow
 * @Date 2023/4/3 17:07
 */
@Data
public class PageVo {

    /**
     * 请求页码，默认第 1 页
     */
    private int pageNum = 1;

    /**
     * 每页大小，默认每页 10 条
     */
    private int pageSize = 10;

    /**
     * 是否查询所有，默认不查所有 为 true 时，pageNum 和 pageSize 无效
     */
    private boolean fetchAll = false;

}
