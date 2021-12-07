package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.config.responseEntity.StatusEnum;
import com.example.duksunggoodsserver.model.dto.response.PromotionResponseDto;
import com.example.duksunggoodsserver.model.dto.request.PromotionRequestDto;
import com.example.duksunggoodsserver.service.PromotionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
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

        // res.put("size", promotionResponseDtoList.size());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ResponseData responseData = new ResponseData();
        responseData.setStatus(StatusEnum.OK);
        responseData.setMessage("OK");
        responseData.setData(promotionResponseDtoList);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseData);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "배너 1개 조회")
    public ResponseEntity getPromotion(@PathVariable Long id){

        PromotionResponseDto promotionResponseDto = promotionService.getPromotion(id);

        if (promotionResponseDto == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ResponseData responseData = new ResponseData();
        responseData.setStatus(StatusEnum.OK);
        responseData.setMessage("OK");
        responseData.setData(promotionResponseDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseData);
    }

    @PostMapping("/")
    @ApiOperation(value = "배너 생성")
    public ResponseEntity createPromotion(@RequestBody PromotionRequestDto requestDto){

        PromotionResponseDto promotionResponseDto = promotionService.createPromotion(requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ResponseData responseData = new ResponseData();
        responseData.setStatus(StatusEnum.OK);
        responseData.setMessage("OK");
        responseData.setData(promotionResponseDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseData);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "배너 삭제")
    public ResponseEntity deletePromotion(@PathVariable Long id){

        Long promotion = promotionService.deletePromotion(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ResponseData responseData = new ResponseData();
        responseData.setStatus(StatusEnum.OK);
        responseData.setMessage("OK");
        responseData.setData(promotion);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseData);
    }
}
