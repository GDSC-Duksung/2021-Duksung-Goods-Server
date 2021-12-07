package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.config.responseEntity.StatusEnum;
import com.example.duksunggoodsserver.model.dto.response.ItemResponseDto;
import com.example.duksunggoodsserver.service.ItemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
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
    public ResponseEntity getItemDetail(@PathVariable Long id) {

        ItemResponseDto itemResponseDto = itemService.getItemDetail(id);

        if (itemResponseDto == null) {
            // res.put("reason", "일치하는 제품 정보가 없습니다. 제품 id를 확인해주세요.");
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ResponseData responseData = new ResponseData();
        responseData.setStatus(StatusEnum.OK);
        responseData.setMessage("OK");
        responseData.setData(itemResponseDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseData);
    }

    @GetMapping("/all")
    @ApiOperation(value = "모든 굿즈 조회")
    public ResponseEntity getAllItems() {

        List<ItemResponseDto> itemResponseDtoList = itemService.getAllItems();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ResponseData responseData = new ResponseData();
        responseData.setStatus(StatusEnum.OK);
        responseData.setMessage("OK");
        responseData.setData(itemResponseDtoList);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseData);
    }
}
