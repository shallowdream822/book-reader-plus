package com.tyf.bookreaderplus.promotion.controller;

import com.tyf.bookreaderplus.common.component.Result;
import com.tyf.bookreaderplus.promotion.service.FlashPromotionService;
import com.tyf.bookreaderplus.promotion.dto.BookPromotionDto;
import com.tyf.bookreaderplus.promotion.dto.PromotionListDto;
import com.tyf.bookreaderplus.promotion.dto.PromotionSessionDto;
import com.tyf.bookreaderplus.promotion.dto.PromotionDto;
import com.tyf.bookreaderplus.promotion.vo.SeckillVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/11/22 11:22
 */
@RestController
@RequestMapping("/api/promotion")
@Api(tags = "秒杀活动接口")
public class PromotionController {

    @Autowired
    private FlashPromotionService flashPromotionService;

    @GetMapping("/promotion_list")
    @ApiOperation(value = "获取活动列表")
    public Result<PromotionListDto> getPromotionList(){
        List<PromotionDto> promotionList = flashPromotionService.getPromotionList();
        PromotionListDto promotionListDto = PromotionListDto.builder().promotionList(promotionList).build();
        return Result.ok(promotionListDto);
    }

    @GetMapping("/promotion_detail")
    @ApiOperation(value = "获取活动详情页")
    public Result<PromotionSessionDto> getPromotionDetail(@RequestParam("promotionId") Long promotionId){
        PromotionSessionDto promotionSessionDto = flashPromotionService.getPromotionDetails(promotionId);
        return Result.ok(promotionSessionDto);
    }

    @ApiOperation(value= "获取小说最新相关的活动")
    @GetMapping("/promotion_book")
    public Result<BookPromotionDto> getBookPromotion(@RequestParam("bookId") Long bookId){
        BookPromotionDto bookPromotionDto = flashPromotionService.getBookPromotion(bookId);
        return Result.ok(bookPromotionDto);
    }


    @ApiOperation(value="抢购活动商品")
    @PostMapping("seckill")
    public Result seckill(@RequestBody SeckillVo seckillVo){
        flashPromotionService.seckill(seckillVo);
        return Result.ok();
    }
}
