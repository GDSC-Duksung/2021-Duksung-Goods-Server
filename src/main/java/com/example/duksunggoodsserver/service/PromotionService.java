package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.model.entity.Promotion;
import com.example.duksunggoodsserver.model.entity.PromotionRequestDTO;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.repository.ItemRepository;
import com.example.duksunggoodsserver.repository.PromotionRepository;
import com.example.duksunggoodsserver.repository.UserRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {
    @Autowired
    PromotionRepository PromotionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

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

    public Promotion createPromotion(@RequestBody PromotionRequestDTO requestDTO){
        Promotion promotion = new Promotion(requestDTO);
        return PromotionRepository.save(promotion);
    }

    public Long deletePromotion(@PathVariable Long id){
        PromotionRepository.deleteById(id);
        return id;
    }

}
