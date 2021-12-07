package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.config.responseEntity.StatusEnum;
import com.example.duksunggoodsserver.model.dto.response.MessageResponseDto;
import com.example.duksunggoodsserver.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ResponseData responseData = new ResponseData();
        responseData.setStatus(StatusEnum.OK);
        responseData.setMessage("OK");
        responseData.setData(messageResponseDtoList);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseData);
    }

    @GetMapping("/all")
    public ResponseEntity getAllItems() {

        List<MessageResponseDto> messageResponseDtoList = chatService.getAllMessages();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ResponseData responseData = new ResponseData();
        responseData.setStatus(StatusEnum.OK);
        responseData.setMessage("OK");
        responseData.setData(messageResponseDtoList);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseData);
    }
}
