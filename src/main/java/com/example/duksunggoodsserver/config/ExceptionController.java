package com.example.duksunggoodsserver.config;

import com.example.duksunggoodsserver.config.responseEntity.ErrorResponse;
import com.example.duksunggoodsserver.config.responseEntity.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    // 400
    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity BadRequestException(final RuntimeException e) {
        log.warn("error", e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity MethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.warn("error", e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    // 401
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity handleAccessDeniedException(final AccessDeniedException e) {
        log.warn("error", e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(StatusEnum.UNAUTHORIZED)
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    // 500
    @ExceptionHandler({ Exception.class })
    public ResponseEntity handleAll(final Exception e) {
        log.info(e.getClass().getName());
        log.error("error", e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(StatusEnum.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
        return ResponseEntity.internalServerError()
                .body(errorResponse);
    }
}