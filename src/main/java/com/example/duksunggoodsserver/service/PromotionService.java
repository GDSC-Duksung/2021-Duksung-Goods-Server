package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.model.entity.Promotion;
import com.example.duksunggoodsserver.model.entity.PromotionRequestDto;
import com.example.duksunggoodsserver.repository.PromotionRepository;
import com.example.duksunggoodsserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {
    @Autowired
    PromotionRepository PromotionRepository;

    public List<Promotion> getAllPromotions(){
        List<Promotion> promotionList = PromotionRepository.findAll();

        //랜덤섞기
        Collections.shuffle(promotionList);

        //추출
        if(promotionList.size() <4){
            return promotionList;
        }
        else{
            return promotionList.subList(0,4);
        }
    }

    public Optional<Promotion> getPromotion(Long id){
        return PromotionRepository.findItemById(id);
    }

    public Promotion createPromotion(@RequestBody PromotionRequestDto requestDto){
        Promotion promotion = new Promotion(requestDto);
        return PromotionRepository.save(promotion);
    }
}
