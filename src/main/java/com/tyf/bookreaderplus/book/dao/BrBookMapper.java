package com.tyf.bookreaderplus.book.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyf.bookreaderplus.book.dto.BookDto;
import com.tyf.bookreaderplus.book.entity.BrBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (BrBook)表数据库访问层
 *
 * @author shallow
 * @since 2023-04-01 17:54:57
 */
@Mapper
public interface BrBookMapper extends BaseMapper<BrBook> {
    BookDto getBookDtoById(@Param("bookId") Long bookId);
}

