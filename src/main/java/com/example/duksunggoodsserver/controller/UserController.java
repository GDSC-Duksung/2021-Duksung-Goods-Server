package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.model.dto.response.BuyResponseDto;
import com.example.duksunggoodsserver.model.dto.response.ItemResponseDto;
import com.example.duksunggoodsserver.service.BuyService;
import com.example.duksunggoodsserver.service.ItemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final BuyService buyService;
    private final ItemService itemService;

    @GetMapping("/mypage/{id}/buy")
    @ApiOperation(value = "구매 목록 조회")
    public ResponseEntity<List<BuyResponseDto>> getBuyList(@PathVariable Long id) {
        return ResponseEntity.ok(buyService.getBuyList(id));
    }

    @GetMapping("/mypage/{id}/sell")
    @ApiOperation(value = "판매 목록 조회")
    public ResponseEntity<List<ItemResponseDto>> getSellList(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemList(id));
    }

    @GetMapping("/mypage/{id}/sell/{itemId}")
    @ApiOperation(value = "입금자 목록 조회", notes = "내 판매 아이템의 입금자 목록을 조회한다.")
    public ResponseEntity<List<BuyResponseDto>> getSellFormList(@PathVariable Long id, @PathVariable Long itemId) {
        return ResponseEntity.ok(buyService.getSellFormList(itemId));
    }
}
