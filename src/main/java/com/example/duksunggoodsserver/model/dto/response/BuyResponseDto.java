package com.example.duksunggoodsserver.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyResponseDto {

    private Long id;

    private UserResponseDto user;

    private ItemResponseDto item;

    private Integer count;

    private LocalDateTime createdAt;
}
