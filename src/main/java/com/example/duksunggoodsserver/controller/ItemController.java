package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.model.dto.response.ItemResponseDto;
import com.example.duksunggoodsserver.service.ItemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "굿즈 상세정보 조회")
    public ResponseEntity<Map<String, Object>> getItemDetail(@PathVariable Long id) {

        ItemResponseDto itemResponseDto = itemService.getItemDetail(id);
        Map<String, Object> res = new HashMap<>();

        if (itemResponseDto == null) {
            res.put("result", "FAIL");
            res.put("reason", "일치하는 제품 정보가 없습니다. 제품 id를 확인해주세요.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(res);
        }

        res.put("result", "SUCCESS");
        res.put("data", itemResponseDto);

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/all")
    @ApiOperation(value = "모든 굿즈 조회")
    public ResponseEntity<Map<String, Object>> getAllItems() {

        List<ItemResponseDto> itemResponseDtoList = itemService.getAllItems();
        Map<String, Object> res = new HashMap<>();

        res.put("result", "SUCCESS");
        res.put("data", itemResponseDtoList);

        return ResponseEntity.ok().body(res);
    }
}
