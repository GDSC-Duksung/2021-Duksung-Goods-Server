package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.model.dto.response.ItemResponseDto;
import com.example.duksunggoodsserver.service.ItemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/home")
    @ApiOperation(value = "홈 아이템 조회 - 성공임박굿즈, 인기굿즈, 신규굿즈 2개씩")
    public ResponseEntity getHomeItem(HttpServletRequest req) {
        List<ItemResponseDto> itemResponseDtoList = itemService.getHomeItem(req);
        log.info("Succeeded in getting home item : viewer {} => {}", 1, itemResponseDtoList);
        ResponseData responseData = ResponseData.builder()
                .data(itemResponseDtoList)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @GetMapping("/{itemId}")
    @ApiOperation(value = "굿즈 상세정보 조회")
    public ResponseEntity getItemDetail(HttpServletRequest req, @PathVariable Long itemId) {
        ItemResponseDto itemResponseDto = itemService.getItemDetail(req, itemId);
        log.info("Succeeded in getting detailed item : viewer {} => {}", 1, itemResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(itemResponseDto)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @GetMapping("/demand-survey-type/{demandSurveyTypeId}/category/{categoryId}")
    @ApiOperation(value = "수요조사별 및 카테고리별 굿즈 조회")
    public ResponseEntity getItemListByDemandSurveyTypeAndCategory(HttpServletRequest req, @PathVariable Long demandSurveyTypeId, @PathVariable Long categoryId, @RequestParam int page) {
        List<ItemResponseDto> itemResponseDtoList = itemService.getItemListByDemandSurveyTypeAndCategory(req, demandSurveyTypeId, categoryId, page);
        log.info("Succeeded in getting items by demandSurveyType and category : viewer {} => {}", 1, itemResponseDtoList);
        ResponseData responseData = ResponseData.builder()
                .data(itemResponseDtoList)
                .build();
        return ResponseEntity.ok().body(responseData);
    }
}
