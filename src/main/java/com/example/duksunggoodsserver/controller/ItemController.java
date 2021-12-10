package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ErrorResponse;
import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.config.responseEntity.StatusEnum;
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

import java.util.List;

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
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(StatusEnum.NOT_FOUND)
                    .message("id가 null")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        ResponseData responseData = ResponseData.builder()
                .data(itemResponseDto)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }

    @GetMapping("/all")
    @ApiOperation(value = "모든 굿즈 조회")
    public ResponseEntity getAllItems() {

        List<ItemResponseDto> itemResponseDtoList = itemService.getAllItems();

        ResponseData responseData = ResponseData.builder()
                .data(itemResponseDtoList)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }
}
