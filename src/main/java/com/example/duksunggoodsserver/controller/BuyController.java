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
import java.util.List;

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

    @GetMapping("/mypage/sell/{itemId}")
    @ApiOperation(value = "입금 목록 조회", notes = "내 판매 아이템의 입금 목록을 조회한다.")
    public ResponseEntity getDepositList(@PathVariable Long itemId) {

        List<BuyResponseDto> buyResponseDtoList = buyService.getDepositList(itemId);
        log.info("Succeeded in getting deposit of item : viewer {} => {}", 1, buyResponseDtoList);
        ResponseData responseData = ResponseData.builder()
                .data(buyResponseDtoList)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }

    @PatchMapping("/mypage/sell/{buyId}")
    @ApiOperation(value = "입금 변경 체크")
    public ResponseEntity patchDeposit(@PathVariable Long buyId) {

        boolean result = buyService.changeDeposit(buyId);
        log.info("Succeeded in patching deposit of item : viewer {} => {}", 1, result);
        ResponseData responseData = ResponseData.builder()
                .data(result)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }

    @GetMapping("/mypage/buy")
    @ApiOperation(value = "구매 목록 조회")
    public ResponseEntity getBuyList() {

        List<BuyResponseDto> buyResponseDtoList = buyService.getBuyList();
        log.info("Succeeded in getting buyList of item : viewer {} => {}", 1, buyResponseDtoList);
        ResponseData responseData = ResponseData.builder()
                .data(buyResponseDtoList)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }
}
