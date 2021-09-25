package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.model.entity.Buy;
import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping("/item/{id}")
    public Map<String, Object> getItemDetail(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Item> item = itemService.getItemDetail(id);

        if (item.isEmpty()) {
            response.put("result", "FAIL");
            response.put("reason", "일치하는 제품 정보가 없습니다. 제품 id를 확인해주세요.");
            return response;
        }

        response.put("result", "SUCCESS");
        response.put("data", item);

        return response;
    }
}
