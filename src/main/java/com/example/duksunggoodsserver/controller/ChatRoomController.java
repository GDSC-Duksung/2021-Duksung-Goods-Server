package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.model.entity.ChatRoom;
import com.example.duksunggoodsserver.model.entity.ChatRoomJoin;
import com.example.duksunggoodsserver.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatService chatService;

    @GetMapping("/rooms")
    public List<ChatRoom> room() {
        return chatService.getChatRoomList();
    }

    @PostMapping("/room")
    public ChatRoom createChatRoomWithOne(@RequestParam String name) {
        return chatService.createChatRoomWithOne(name);
    }

    @PostMapping("/room/manyPeople")
    public ChatRoom createChatRoomWithMany(@RequestParam String name, @RequestBody List<Long> userIdList) {
        return chatService.createChatRoomWithMany(name, userIdList);
    }

    @PostMapping("/room/{roomId}/{userId}")
    public ChatRoomJoin addUserToChatRoom(@PathVariable Long roomId, @PathVariable Long userId) {
        return chatService.addUserToChatRoom(roomId, userId);
    }

    @DeleteMapping("/room/{roomId}")
    public Optional<ChatRoomJoin> deleteUserFromChatRoom(@PathVariable Long roomId) {
        return chatService.deleteUserFromChatRoom(roomId);
    }

    @GetMapping("/room/{roomUUID}")
    public ChatRoom roomInfo(@PathVariable String roomUUID) {
        return chatService.getChatRoom(roomUUID);
    }
}
