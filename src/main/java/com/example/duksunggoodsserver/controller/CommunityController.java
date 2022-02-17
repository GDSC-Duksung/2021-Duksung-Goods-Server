package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.model.dto.request.CommunityRequestDto;
import com.example.duksunggoodsserver.model.dto.response.CommunityResponseDto;
import com.example.duksunggoodsserver.service.CommunityService;
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
public class CommunityController {
    private final CommunityService communityService;

    @GetMapping("/{itemId}/communities")
    @ApiOperation(value = "커뮤니티 조회")
    public ResponseEntity getCommunityList(@PathVariable Long itemId) {

        List<CommunityResponseDto> communityResponseDtoList = communityService.getCommunityList(itemId);
        log.info("Succeeded in getting communities of item : viewer {} => {}", 1, communityResponseDtoList);
        ResponseData responseData = ResponseData.builder()
                .data(communityResponseDtoList)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }

    @PostMapping("/{itemId}/community")
    @ApiOperation(value = "커뮤니티 생성")
    public ResponseEntity postCommunity(@PathVariable Long itemId, @Valid @RequestBody CommunityRequestDto communityRequestDto) {

        CommunityResponseDto communityResponseDto = communityService.saveCommunity(itemId, communityRequestDto);
        log.info("Succeeded in posting community of item : viewer {} => {}", 1, communityResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(communityResponseDto)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }

    @DeleteMapping("/community/{id}")
    @ApiOperation(value = "커뮤니티 삭제")
    public ResponseEntity deleteComment(@PathVariable Long id) {

        Long community = communityService.deleteCommunity(id);
        log.info("Succeeded in deleting community of item : viewer {} => {}", 1, community);
        ResponseData responseData = ResponseData.builder()
                .data(community)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }
}
