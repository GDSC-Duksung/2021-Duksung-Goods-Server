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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BuyController {

    private final BuyService buyService;

    @GetMapping("/buy-items")
    @ApiOperation(value = "구매 목록 조회")
    public ResponseEntity getBuyList(HttpServletRequest req) {
        List<BuyResponseDto> buyResponseDtoList = buyService.getBuyList(req);
        log.info("Succeeded in getting buyList of item : viewer {} => {}", 1, buyResponseDtoList);
        ResponseData responseData = ResponseData.builder()
                .data(buyResponseDtoList)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @GetMapping("/buy-items/{buyId}")
    @ApiOperation(value = "구매 목록 상세 조회")
    public ResponseEntity getBuyDetail(@PathVariable Long buyId) {
        BuyResponseDto buyResponseDto = buyService.getBuyDetail(buyId);
        log.info("Succeeded in getting buy detail of item : viewer {} => {}", 1, buyResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(buyResponseDto)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @PostMapping("items/{itemId}/buy-items")
    @ApiOperation(value = "구매폼 생성")
    public ResponseEntity postBuyForm(HttpServletRequest req,
                                      @PathVariable Long itemId,
                                      @Valid @RequestBody BuyRequestDto buyRequestDto) {
        BuyResponseDto buyResponseDto = buyService.createBuyForm(req, itemId, buyRequestDto);
        log.info("Succeeded in posting buyForm : viewer {} => {}", 1, buyResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(buyResponseDto)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @DeleteMapping("/buy-items/{buyId}")
    @ApiOperation(value = "구매폼 삭제")
    public ResponseEntity deleteBuyForm(@PathVariable Long buyId) {
        Long buyForm = buyService.deleteBuyForm(buyId);
        log.info("Succeeded in deleting buyForm : viewer {} => {}", 1, buyForm);
        ResponseData responseData = ResponseData.builder()
                .data(buyForm)
                .build();
        return ResponseEntity.ok().body(responseData);
    }
}
