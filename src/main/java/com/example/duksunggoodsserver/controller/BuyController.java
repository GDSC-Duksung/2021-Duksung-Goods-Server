package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.model.dto.request.BuyRequestDto;
import com.example.duksunggoodsserver.model.dto.response.BuyResponseDto;
import com.example.duksunggoodsserver.service.BuyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/item")
public class BuyController {

    private final BuyService buyService;

    @PostMapping("/{itemId}/buy")
    @ApiOperation(value = "구매폼 생성")
    public ResponseEntity postBuyForm(@PathVariable Long itemId, @Valid @RequestBody BuyRequestDto buyRequestDto) {

        BuyResponseDto buyResponseDto = buyService.createBuyForm(itemId, buyRequestDto);
        log.info("Succeeded in posting buyForm : viewer {} => {}", 1, buyResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(buyResponseDto)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }

    @DeleteMapping("/buy/{buyId}")
    @ApiOperation(value = "구매폼 삭제")
    public ResponseEntity deleteBuyForm(@PathVariable Long buyId) {

        Long buyForm = buyService.deleteBuyForm(buyId);
        log.info("Succeeded in deleting buyForm : viewer {} => {}", 1, buyForm);
        ResponseData responseData = ResponseData.builder()
                .data(buyForm)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }
}
