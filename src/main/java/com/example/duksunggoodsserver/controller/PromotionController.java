package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.model.dto.response.PromotionResponseDto;
import com.example.duksunggoodsserver.model.dto.request.PromotionRequestDto;
import com.example.duksunggoodsserver.service.PromotionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping("/")
    @ApiOperation(value = "모든 배너 조회")
    public ResponseEntity<Map<String, Object>> getAllPromotions(){
        List<PromotionResponseDto> promotionResponseDtoList = promotionService.getAllPromotions();

        Map<String, Object> res = new HashMap<>();
        res.put("data", promotionResponseDtoList);
        res.put("size", promotionResponseDtoList.size());

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "배너 1개 조회")
    public ResponseEntity<Map<String, Object>> getPromotion(@PathVariable Long id){
        PromotionResponseDto promotionResponseDto = promotionService.getPromotion(id);

        if(promotionResponseDto == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> res = new HashMap<>();
        res.put("data", promotionResponseDto);

        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/")
    @ApiOperation(value = "배너 생성")
    public ResponseEntity createPromotion(@RequestBody PromotionRequestDto requestDto){
        PromotionResponseDto promotionResponseDto = promotionService.createPromotion(requestDto);
        return ResponseEntity.ok().body(promotionResponseDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "배너 삭제")
    public ResponseEntity<Map<String, Object>> deletePromotion(@PathVariable Long id){
        Long promotion = promotionService.deletePromotion(id);

        Map<String, Object> res = new HashMap<>();
        res.put("data", promotion);

        return ResponseEntity.ok().body(res);
    }
}
