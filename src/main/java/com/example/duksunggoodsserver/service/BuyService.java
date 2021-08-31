package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.model.entity.Buy;
import com.example.duksunggoodsserver.repository.BuyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyService {
    @Autowired
    private BuyRepository buyRepository;

    public List<Buy> getBuyList(Long id) {
        List<Buy> buy = buyRepository.findAllByUserId(id);
        return buy;
    }
}