package com.example.duksunggoodsserver.model.dto.request;

import com.example.duksunggoodsserver.model.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatRequestDto {

    private MessageType type; // 메시지 타입

    private String roomUUID; // 방번호

    private String sender; // 메시지 보낸사람

    private Long senderId;

    private String message; // 메시지

    private LocalDateTime time;

    public Chat toChatEntity(ChatRoom chatRoom, User user) {
        return Chat.builder()
                .type(this.type)
                .message(message)
                .time(this.time)
                .chatRoom(chatRoom)
                .user(user)
                .build();
    }
}
