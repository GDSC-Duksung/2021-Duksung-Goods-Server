package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.model.entity.Promotion;
import com.example.duksunggoodsserver.model.entity.PromotionRequestDTO;
import com.example.duksunggoodsserver.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/promotions")
public class PromotionController {
    @Autowired
    com.example.duksunggoodsserver.service.PromotionService PromotionService;

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllPromotions(){
        List<Promotion> promotions = PromotionService.getAllPromotions();

        Map<String, Object> res = new HashMap<>();
        res.put("data", promotions);
        res.put("size", promotions.size());

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPromotion(@PathVariable Long id){
        Optional<Promotion> promotion = PromotionService.getPromotion(id);

        if(promotion.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> res = new HashMap<>();
        res.put("data", promotion);

        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/")
    public ResponseEntity createPromotion(@RequestBody PromotionRequestDTO requestDto){
        Promotion promotion = PromotionService.createPromotion(requestDto);
        return ResponseEntity.ok().body(promotion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePromotion(@PathVariable Long id){
        Long promotion = PromotionService.deletePromotion(id);

        Map<String, Object> res = new HashMap<>();
        res.put("data", promotion);

        return ResponseEntity.ok().body(res);
    }
}
