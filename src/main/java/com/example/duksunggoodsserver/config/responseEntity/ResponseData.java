package com.example.duksunggoodsserver.config.responseEntity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseData<T> {

    private LocalDateTime transactionTime;
    private StatusEnum status;
    private String message;
    private T data;

    public ResponseData() {
        this.transactionTime = LocalDateTime.now();
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }
}