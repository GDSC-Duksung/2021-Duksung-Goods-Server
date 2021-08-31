package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.model.entity.Buy;
import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.service.BuyService;
import com.example.duksunggoodsserver.service.ItemService;
import com.example.duksunggoodsserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    BuyService buyService;
    @Autowired
    ItemService itemService;

    @GetMapping("/mypage/{id}")
    public Map<String, Object> getBuyAndSellList(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> user = userService.getUserById(id);

        if (user.isEmpty()) {
            response.put("result", "FAIL");
            response.put("reason", "일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요.");
            return response;
        }

        List<Buy> buyList = buyService.getBuyList(id);
        List<Item> sellList = itemService.getItemList(id);

        HashMap<Integer, List> buyAndSellList = new HashMap<>() {{
            put(1, buyList);
            put(2, sellList);
        }};

        response.put("result", "SUCCESS");
        response.put("data", buyAndSellList);

        // TODO: 추후에 ResponseEntity 적용
        return response;
    }
}
