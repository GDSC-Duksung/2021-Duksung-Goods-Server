package com.example.duksunggoodsserver.model.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDto {

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String phoneNumber;

    private String address;
}
