package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.model.dto.request.ChatRequestDto;
import com.example.duksunggoodsserver.model.dto.response.ChatResponseDto;
import com.example.duksunggoodsserver.model.entity.MessageType;
import com.example.duksunggoodsserver.service.ChatService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(ChatRequestDto chatRequestDto) {
        chatRequestDto.setTime(LocalDateTime.now().withNano(0));
        if (MessageType.ENTER.equals(chatRequestDto.getType()) && !chatService.existEnterChat(chatRequestDto.getSenderId(), chatRequestDto.getRoomUUID()))
            chatRequestDto.setMessage(chatRequestDto.getSender() + "님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/sub/chat/room/" + chatRequestDto.getRoomUUID(), chatRequestDto);

        // 입장 관련 채팅은 한 번만 저장
        if (!MessageType.ENTER.equals(chatRequestDto.getType()) || !chatService.existEnterChat(chatRequestDto.getSenderId(), chatRequestDto.getRoomUUID()))
            chatService.saveChat(chatRequestDto);
    }

    @GetMapping("/api/chat/{roomUUID}")
    @ResponseBody
    @ApiOperation(value = "채팅룸에 채팅 조회")
    public ResponseEntity getChatListInChatRoom(HttpServletRequest req, @PathVariable String roomUUID) {
        List<ChatResponseDto> chatResponseDtoList = chatService.getChatListInChatRoom(req, roomUUID);
        log.info("Succeeded in getting chatList in chatRoom : viewer {} => {}", 1, chatResponseDtoList);
        ResponseData responseData = ResponseData.builder()
                .data(chatResponseDtoList)
                .build();
        return ResponseEntity.ok().body(responseData);
    }
}
