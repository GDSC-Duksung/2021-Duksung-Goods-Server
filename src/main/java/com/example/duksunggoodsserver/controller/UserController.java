package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.model.dto.response.BuyResponseDto;
import com.example.duksunggoodsserver.model.dto.response.ItemResponseDto;
import com.example.duksunggoodsserver.model.dto.response.UserResponseDto;
import com.example.duksunggoodsserver.service.BuyService;
import com.example.duksunggoodsserver.service.ItemService;
import com.example.duksunggoodsserver.service.UserService;
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
    private final UserService userService;

    @GetMapping("/info")
    @ApiOperation(value = "유저 정보 조회")
    public ResponseEntity getUser() {

        UserResponseDto userResponseDto = userService.getUser();
        log.info("Succeeded in getting user : viewer {} => {}", 1, userResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(userResponseDto)
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

    @GetMapping("/mypage/sell")
    @ApiOperation(value = "판매 목록 조회")
    public ResponseEntity getSellList() {

        List<ItemResponseDto> itemResponseDtoList = itemService.getItemList();
        log.info("Succeeded in getting sellList of item : viewer {} => {}", 1, itemResponseDtoList);
        ResponseData responseData = ResponseData.builder()
                .data(itemResponseDtoList)
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
}
