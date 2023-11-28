package com.tyf.bookreaderplus.promotion.service;

import com.tyf.bookreaderplus.promotion.dto.BookPromotionDto;
import com.tyf.bookreaderplus.promotion.dto.PromotionSessionDto;
import com.tyf.bookreaderplus.promotion.dto.PromotionDto;
import com.tyf.bookreaderplus.promotion.vo.SeckillVo;

import java.util.List;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/11/20 16:55
 */
public interface FlashPromotionService {

    void uploadPromotionInfo();

    List<PromotionDto> getPromotionList();

    PromotionSessionDto getPromotionDetails(Long promotionId);

    BookPromotionDto getBookPromotion(Long bookId);

    void seckill(SeckillVo seckillVo);
}
