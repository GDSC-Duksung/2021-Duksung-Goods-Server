package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ErrorResponse;
import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.config.responseEntity.StatusEnum;
import com.example.duksunggoodsserver.model.dto.response.CommentResponseDto;
import com.example.duksunggoodsserver.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/community")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}/comments")
    @ApiOperation(value = "댓글 조회")
    public ResponseEntity getCommentList(@PathVariable Long id) {

        List<CommentResponseDto> commentResponseDtoList = commentService.getCommentList(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ResponseData responseData = ResponseData.builder()
                .data(commentResponseDtoList)
                .build();

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseData);
    }

    @PostMapping("/{id}/comment")
    @ApiOperation(value = "댓글 생성")
    public ResponseEntity postComment(@PathVariable Long id, @RequestBody Map<String, String> comment) {

        CommentResponseDto commentResponseDto = commentService.saveComment(id, comment);

        if (commentResponseDto == null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(StatusEnum.NOT_FOUND)
                    .message("communityId 혹은 userId가 null")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ResponseData responseData = ResponseData.builder()
                .data(commentResponseDto)
                .build();

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseData);
    }

    @DeleteMapping("/comment/{id}")
    @ApiOperation(value = "댓글 삭제")
    public ResponseEntity deleteComment(@PathVariable Long id) {

        Long comment = commentService.deleteComment(id);

        if (comment == null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(StatusEnum.NOT_FOUND)
                    .message("id가 null")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ResponseData responseData = ResponseData.builder()
                .data(comment)
                .build();

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseData);
    }
}
