package com.example.duksunggoodsserver.model.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ChatRoomRequestDto {

    private String name;

    private List<Long> userIdList;
}
