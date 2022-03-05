package com.example.duksunggoodsserver.model.dto.request;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class ChatRoomRequestDto {

    private String name;

    private List<Long> userIdList;
}
