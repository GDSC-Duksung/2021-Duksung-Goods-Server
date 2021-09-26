package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.model.entity.Promotion;
import com.example.duksunggoodsserver.repository.PromotionRepository;
import com.example.duksunggoodsserver.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/promotions")
public class PromotionController {
    @Autowired
    PromotionService PromotionService;

    @GetMapping("/")
    public List<Promotion> getAllPromotions(){
        return PromotionService.getAllPromotions();
    }
}
