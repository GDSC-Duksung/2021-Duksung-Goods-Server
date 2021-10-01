package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.model.entity.Buy;
import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.service.BuyService;
import com.example.duksunggoodsserver.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    BuyService buyService;
    @Autowired
    ItemService itemService;

    @GetMapping("/mypage/{id}/buy")
    public ResponseEntity<List<Buy>> getBuyList(@PathVariable Long id) {
        return ResponseEntity.ok(buyService.getBuyList(id));
    }

    @GetMapping("/mypage/{id}/sell")
    public ResponseEntity<List<Item>> getSellList(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemList(id));
    }
}
