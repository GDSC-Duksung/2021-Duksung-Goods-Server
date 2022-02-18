package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.config.responseEntity.ResponseData;
import com.example.duksunggoodsserver.model.dto.request.CommentRequestDto;
import com.example.duksunggoodsserver.model.dto.response.CommentResponseDto;
import com.example.duksunggoodsserver.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        log.info("Succeeded in getting comments of community : viewer {} => {}", 1, commentResponseDtoList);
        ResponseData responseData = ResponseData.builder()
                .data(commentResponseDtoList)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }

    @PostMapping("/{id}/comment")
    @ApiOperation(value = "댓글 생성")
    public ResponseEntity postComment(@PathVariable Long id, @Valid @RequestBody CommentRequestDto commentRequestDto) {

        CommentResponseDto commentResponseDto = commentService.saveComment(id, commentRequestDto);
        log.info("Succeeded in posting comment of community : viewer {} => {}", 1, commentResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(commentResponseDto)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }

    @DeleteMapping("/comment/{id}")
    @ApiOperation(value = "댓글 삭제")
    public ResponseEntity deleteComment(@PathVariable Long id) {

        Long comment = commentService.deleteComment(id);
        log.info("Succeeded in deleting comment of community : viewer {} => {}", 1, comment);
        ResponseData responseData = ResponseData.builder()
                .data(comment)
                .build();

        return ResponseEntity.ok()
                .body(responseData);
    }
}
