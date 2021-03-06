package com.example.duksunggoodsserver.model.dto.response;

import com.example.duksunggoodsserver.model.entity.ChatRoom;
import com.example.duksunggoodsserver.model.entity.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResponseDto {

    private Long id;

    private String message;

    private LocalDateTime time;

    private ChatRoom chatRoom;

    private UserResponseDto user;

    private String sender;

    private MessageType type;
}
