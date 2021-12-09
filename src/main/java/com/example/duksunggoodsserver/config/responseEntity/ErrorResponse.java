package com.example.duksunggoodsserver.config.responseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {

    @Builder.Default
    private LocalDateTime transactionTime = LocalDateTime.now();

    @Builder.Default
    private StatusEnum status = StatusEnum.BAD_REQUEST;

    private String message;
}
