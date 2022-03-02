package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.model.dto.request.ChatRoomRequestDto;
import com.example.duksunggoodsserver.model.dto.response.ChatRoomJoinResponseDto;
import com.example.duksunggoodsserver.model.dto.response.ChatRoomResponseDto;
import com.example.duksunggoodsserver.service.ChatRoomService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/rooms")
    @ApiOperation(value = "채팅방 리스트 조회")
    public ResponseEntity getChatRoomList(HttpServletRequest req) {
        List<ChatRoomResponseDto> chatRoomResponseDtoList = chatRoomService.getChatRoomList(req);
        log.info("Succeeded in getting chatRooms : viewer {} => {}", 1, chatRoomResponseDtoList);
        ResponseData responseData = ResponseData.builder()
                .data(chatRoomResponseDtoList)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @GetMapping("/room/{roomUUID}")
    @ApiOperation(value = "채팅방 1개 조회")
    public ResponseEntity getChatRoom(HttpServletRequest req, @PathVariable String roomUUID) {
        ChatRoomResponseDto chatRoomResponseDto = chatRoomService.getChatRoom(req, roomUUID);
        log.info("Succeeded in getting a chatRoom : viewer {} => {}", 1, chatRoomResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(chatRoomResponseDto)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @PostMapping("/room")
    @ApiOperation(value = "1:N 채팅방 생성")
    public ResponseEntity createChatRoom(HttpServletRequest req, @RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        ChatRoomResponseDto chatRoomResponseDto = chatRoomService.createChatRoom(req, chatRoomRequestDto.getName(), chatRoomRequestDto.getUserIdList());
        log.info("Succeeded in posting a 1:N chatRoom : viewer {} => {}", 1, chatRoomResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(chatRoomResponseDto)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @PostMapping("/room/{roomId}/{userId}")
    @ApiOperation(value = "채팅방 유저 추가")
    public ResponseEntity addUserToChatRoom(HttpServletRequest req, @PathVariable Long roomId, @PathVariable Long userId) {
        ChatRoomJoinResponseDto chatRoomJoinResponseDto = chatRoomService.addUserToChatRoom(req, roomId, userId);
        log.info("Succeeded in adding user to chatRoom : viewer {} => {}", 1, chatRoomJoinResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(chatRoomJoinResponseDto)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @DeleteMapping("/room/{roomId}")
    @ApiOperation(value = "채팅방 나가기")
    public ResponseEntity deleteUserFromChatRoom(HttpServletRequest req, @PathVariable Long roomId) {
        Long chatRoomId = chatRoomService.deleteUserFromChatRoom(req, roomId);
        log.info("Succeeded in deleting user from chatRoom : viewer {} => {}", 1, chatRoomId);
        ResponseData responseData = ResponseData.builder()
                .data(chatRoomId)
                .build();
        return ResponseEntity.ok().body(responseData);
    }
}
