package com.tyf.bookreaderplus.book.vo;

import com.tyf.bookreaderplus.common.component.PageVo;
import lombok.Data;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/4/3 17:09
 */
@Data
public class CommentPageVo extends PageVo {

    private Long bookId;

    private Long parentId;

    /**
     * 排序方式， 0为按点赞数排序，1为按发表时间排序
     */
    private Integer sort = 0;
}
