package com.example.duksunggoodsserver.model.dto.response;

import com.example.duksunggoodsserver.model.entity.ChatRoom;

import java.time.LocalDateTime;

public class MessageResponseDto {

    private Long id;

    private String message;

    private LocalDateTime time;

    private ChatRoom chatRoom;

    private UserResponseDto user;
}
