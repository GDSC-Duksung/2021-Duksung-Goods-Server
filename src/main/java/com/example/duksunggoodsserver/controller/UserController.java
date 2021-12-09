package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.config.responseEntity.StatusEnum;
import com.example.duksunggoodsserver.model.dto.response.BuyResponseDto;
import com.example.duksunggoodsserver.model.dto.response.ItemResponseDto;
import com.example.duksunggoodsserver.service.BuyService;
import com.example.duksunggoodsserver.service.ItemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
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
    public ResponseEntity getBuyList(@PathVariable Long id) {

        List<BuyResponseDto> buyResponseDtoList = buyService.getBuyList(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ResponseData responseData = ResponseData.builder()
                .data(buyResponseDtoList)
                .build();

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseData);
    }

    @GetMapping("/mypage/{id}/sell")
    @ApiOperation(value = "판매 목록 조회")
    public ResponseEntity getSellList(@PathVariable Long id) {

        List<ItemResponseDto> itemResponseDtoList = itemService.getItemList(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ResponseData responseData = ResponseData.builder()
                .data(itemResponseDtoList)
                .build();

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseData);
    }

    @GetMapping("/mypage/{id}/sell/{itemId}")
    @ApiOperation(value = "입금자 목록 조회", notes = "내 판매 아이템의 입금자 목록을 조회한다.")
    public ResponseEntity getSellFormList(@PathVariable Long id, @PathVariable Long itemId) {

        List<BuyResponseDto> buyResponseDtoList = buyService.getSellFormList(itemId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ResponseData responseData = ResponseData.builder()
                .data(buyResponseDtoList)
                .build();

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseData);
    }
}
