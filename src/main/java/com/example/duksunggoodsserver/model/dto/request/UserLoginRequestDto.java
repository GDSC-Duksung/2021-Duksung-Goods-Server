package com.example.duksunggoodsserver.model.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginRequestDto {
    private String email;

    private String password;
}
