package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.model.entity.Promotion;
import com.example.duksunggoodsserver.repository.PromotionRepository;
import com.example.duksunggoodsserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {
    @Autowired
    PromotionRepository PromotionRepository;

    public List<Promotion> getAllPromotions(){
        List<Promotion> promotionList = PromotionRepository.findAll();
        return  promotionList;
    }
}
