package com.tyf.bookreaderplus.book.service;

import com.tyf.bookreaderplus.book.dto.*;
import com.tyf.bookreaderplus.common.component.Result;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Description 小说模块，业务层
 * @Author shallow
 * @Date 2023/4/2 9:51
 */

public interface BookService {
    List<BookCategoryDto> listCategory();

    BookInfoDto getBookById(Long bookId);

    void addVisitCount(Long bookId);


    List<BookDto> listRecBooks(Long bookId) throws NoSuchAlgorithmException;



    boolean checkBookPurchase(Long bookId);
}
