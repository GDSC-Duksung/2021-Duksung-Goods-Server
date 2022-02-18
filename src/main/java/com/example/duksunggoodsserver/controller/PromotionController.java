package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.model.dto.request.PromotionRequestDto;
import com.example.duksunggoodsserver.model.dto.response.PromotionResponseDto;
import com.example.duksunggoodsserver.service.PromotionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping("/")
    @ApiOperation(value = "모든 배너 조회")
    public ResponseEntity getAllPromotions(){

        List<PromotionResponseDto> promotionResponseDtoList = promotionService.getAllPromotions();
        log.info("Succeeded in getting all promotions : viewer {} => {}", 1, promotionResponseDtoList);
        ResponseData responseData = ResponseData.builder()
                .data(promotionResponseDtoList)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "배너 1개 조회")
    public ResponseEntity getPromotion(@PathVariable Long id){

        PromotionResponseDto promotionResponseDto = promotionService.getPromotion(id);
        log.info("Succeeded in getting promotion : viewer {} => {}", 1, promotionResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(promotionResponseDto)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "배너 생성")
    public ResponseEntity createPromotion(@PathVariable Long id, @Valid @RequestBody PromotionRequestDto promotionRequestDto){

        PromotionResponseDto promotionResponseDto = promotionService.createPromotion(id, promotionRequestDto);
        log.info("Succeeded in posting promotion : viewer {} => {}", 1, promotionResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(promotionResponseDto)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "배너 삭제")
    public ResponseEntity deletePromotion(@PathVariable Long id){

        Long promotion = promotionService.deletePromotion(id);
        log.info("Succeeded in deleting promotion : viewer {} => {}", 1, promotion);
        ResponseData responseData = ResponseData.builder()
                .data(promotion)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }
}
