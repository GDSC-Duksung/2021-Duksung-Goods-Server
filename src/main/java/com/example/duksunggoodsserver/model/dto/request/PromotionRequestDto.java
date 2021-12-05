package com.example.duksunggoodsserver.model.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionRequestDto {

    private String image;

    private String content;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long userId;

    private Long itemId;
}
