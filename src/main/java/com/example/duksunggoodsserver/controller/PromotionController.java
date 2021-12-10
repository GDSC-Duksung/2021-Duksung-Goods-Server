package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ErrorResponse;
import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.config.responseEntity.StatusEnum;
import com.example.duksunggoodsserver.model.dto.request.PromotionRequestDto;
import com.example.duksunggoodsserver.model.dto.response.PromotionResponseDto;
import com.example.duksunggoodsserver.service.PromotionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        if (promotionResponseDto == null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(StatusEnum.NOT_FOUND)
                    .message("id가 null")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        ResponseData responseData = ResponseData.builder()
                .data(promotionResponseDto)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }

    @PostMapping("/")
    @ApiOperation(value = "배너 생성")
    public ResponseEntity createPromotion(@RequestBody PromotionRequestDto requestDto){

        PromotionResponseDto promotionResponseDto = promotionService.createPromotion(requestDto);

        if (promotionResponseDto == null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(StatusEnum.NOT_FOUND)
                    .message("user 혹은 item이 null")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

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

        if (promotion == null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(StatusEnum.NOT_FOUND)
                    .message("id가 null")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        ResponseData responseData = ResponseData.builder()
                .data(promotion)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }
}
