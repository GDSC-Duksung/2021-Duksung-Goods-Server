package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.model.dto.request.ItemRequestDto;
import com.example.duksunggoodsserver.model.dto.response.ItemResponseDto;
import com.example.duksunggoodsserver.service.ItemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
        log.info("Succeeded in getting detailed item : viewer {} => {}", 1, itemResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(itemResponseDto)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @GetMapping("/all")
    @ApiOperation(value = "모든 굿즈 조회")
    public ResponseEntity getAllItems() {
        List<ItemResponseDto> itemResponseDtoList = itemService.getAllItems();
        log.info("Succeeded in getting all items : viewer {} => {}", 1, itemResponseDtoList);
        ResponseData responseData = ResponseData.builder()
                .data(itemResponseDtoList)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @PostMapping("")
    @ApiOperation(value = "굿즈 생성")
    public ResponseEntity postItem(HttpServletRequest req,
                                   @RequestPart(required = false) List<MultipartFile> files,
                                   @Valid @RequestPart ItemRequestDto itemRequestDto) throws IOException {
        ItemResponseDto itemResponseDto = itemService.createItem(req, files, itemRequestDto);
        log.info("Succeeded in posting item : viewer {} => {}", 1, itemResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(itemResponseDto)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @DeleteMapping("/{itemId}")
    @ApiOperation(value = "굿즈 삭제")
    public ResponseEntity deleteItem(@PathVariable Long itemId) throws UnsupportedEncodingException {
        Long item = itemService.deleteItem(itemId);
        log.info("Succeeded in deleting item : viewer {} => {}", 1, item);
        ResponseData responseData = ResponseData.builder()
                .data(item)
                .build();
        return ResponseEntity.ok().body(responseData);
    }
}
