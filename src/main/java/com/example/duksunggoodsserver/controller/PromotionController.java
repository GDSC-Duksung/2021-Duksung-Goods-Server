package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.model.dto.request.PromotionRequestDto;
import com.example.duksunggoodsserver.model.dto.response.PromotionResponseDto;
import com.example.duksunggoodsserver.service.PromotionService;
import com.example.duksunggoodsserver.service.S3Service;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final PromotionService promotionService;
    private final S3Service s3Service;

    @GetMapping("/")
    @ApiOperation(value = "모든 배너 조회")
    public ResponseEntity getAllPromotions(){
        List<PromotionResponseDto> promotionResponseDtoList = promotionService.getAllPromotions();
        log.info("Succeeded in getting all promotions : viewer {} => {}", 1, promotionResponseDtoList);
        ResponseData responseData = ResponseData.builder()
                .data(promotionResponseDtoList)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "배너 1개 조회")
    public ResponseEntity getPromotion(@PathVariable Long id){
        PromotionResponseDto promotionResponseDto = promotionService.getPromotion(id);
        log.info("Succeeded in getting promotion : viewer {} => {}", 1, promotionResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(promotionResponseDto)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "배너 생성")
    public ResponseEntity createPromotion(HttpServletRequest req,
                                          @PathVariable Long id,
                                          @RequestPart MultipartFile file,
                                          @Valid @RequestPart PromotionRequestDto promotionRequestDto) throws IOException {
        if (file != null) {
            String imgPath = s3Service.uploadFile(file);
            promotionRequestDto.setImage(imgPath);
        }

        PromotionResponseDto promotionResponseDto = promotionService.createPromotion(req, id, promotionRequestDto);
        log.info("Succeeded in posting promotion : viewer {} => {}", 1, promotionResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(promotionResponseDto)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "배너 삭제")
    public ResponseEntity deletePromotion(@PathVariable Long id) throws UnsupportedEncodingException {
        Long promotion = promotionService.deletePromotion(id);
        log.info("Succeeded in deleting promotion : viewer {} => {}", 1, promotion);
        ResponseData responseData = ResponseData.builder()
                .data(promotion)
                .build();
        return ResponseEntity.ok().body(responseData);
    }
}
