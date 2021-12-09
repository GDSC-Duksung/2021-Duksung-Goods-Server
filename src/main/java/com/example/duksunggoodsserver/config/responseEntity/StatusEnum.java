package com.example.duksunggoodsserver.config.responseEntity;

public enum StatusEnum {

    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR");

    int code;
    String status;

    StatusEnum(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
