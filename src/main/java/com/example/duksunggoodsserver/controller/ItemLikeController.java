package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.model.entity.ItemLike;
import com.example.duksunggoodsserver.service.ItemLikeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/item")
public class ItemLikeController {

    private final ItemLikeService itemLikeService;

    @GetMapping("/like")
    @ApiOperation(value = "찜 조회")
    public ResponseEntity getItemLike() {

        List<ItemLike> itemLikeList = itemLikeService.getItemLike();
        log.info("Succeeded in getting itemLike : viewer {} => {}", 1, itemLikeList);
        ResponseData responseData = ResponseData.builder()
                .data(itemLikeList)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }

    @GetMapping("{itemId}/like/count")
    @ApiOperation(value = "아이템의 찜 갯수 조회")
    public ResponseEntity getCountOfItemLike(@PathVariable Long itemId) {

        Long count = itemLikeService.getCountOfItemLike(itemId);
        log.info("Succeeded in getting count of itemLike : viewer {} => {}", 1, count);
        ResponseData responseData = ResponseData.builder()
                .data(count)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }

    @PostMapping("/{itemId}/like")
    @ApiOperation(value = "찜 생성 / 삭제")
    public ResponseEntity postItemLike(@PathVariable Long itemId) {

        boolean result = itemLikeService.changeItemLike(itemId);
        log.info("Succeeded in posting itemLike : viewer {} => {}", 1, result);
        ResponseData responseData = ResponseData.builder()
                .data(result)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }
}
