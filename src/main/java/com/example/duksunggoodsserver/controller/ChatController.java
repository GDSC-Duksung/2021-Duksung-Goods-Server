package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.model.dto.response.MessageResponseDto;
import com.example.duksunggoodsserver.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/{id}")
    public ResponseEntity getMessagesById(@PathVariable Long id) {

        List<MessageResponseDto> messageResponseDtoList = chatService.getMessageList(id);

        ResponseData responseData = ResponseData.builder()
                .data(messageResponseDtoList)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }

    @GetMapping("/all")
    public ResponseEntity getAllItems() {

        List<MessageResponseDto> messageResponseDtoList = chatService.getAllMessages();

        ResponseData responseData = ResponseData.builder()
                .data(messageResponseDtoList)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }
}
