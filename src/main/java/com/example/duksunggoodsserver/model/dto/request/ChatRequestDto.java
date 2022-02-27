package com.example.duksunggoodsserver.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequestDto {

    // 메시지 타입 : 입장, 채팅, 퇴장
    public enum MessageType {
        ENTER, TALK, LEAVE
    }

    private MessageType type; // 메시지 타입

    private String roomUUID; // 방번호

    private String sender; // 메시지 보낸사람

    private String message; // 메시지
}
