package com.tyf.bookreaderplus.order.entity.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BrUserStarBook)实体类
 *
 * @author shallow
 * @since 2023-04-01 17:52:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrUserStarBook implements Serializable{
    private static final long serialVersionUID= -78320130858740270L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;


    @TableField(value = "user_id")
    private Long userId;


    @TableField(value = "book_id")
    private Long bookId;

    /**创建时间*/

    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**更新时间*/

    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**逻辑删除 0 -正常，1-删除*/
                @TableLogic

    private Integer deleted;
}

