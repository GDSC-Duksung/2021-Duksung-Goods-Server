package com.example.duksunggoodsserver.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomJoinResponseDto {

    private Long id;

    private ChatRoomResponseDto chatRoom;

    private UserResponseDto user;
}
