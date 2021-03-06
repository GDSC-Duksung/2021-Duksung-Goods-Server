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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/communities")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{communityId}/comments")
    @ApiOperation(value = "댓글 조회")
    public ResponseEntity getCommentList(@PathVariable Long communityId) {
        List<CommentResponseDto> commentResponseDtoList = commentService.getCommentList(communityId);
        log.info("Succeeded in getting comments of community : viewer {} => {}", 1, commentResponseDtoList);
        ResponseData responseData = ResponseData.builder()
                .data(commentResponseDtoList)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @PostMapping("/{communityId}/comments")
    @ApiOperation(value = "댓글 생성")
    public ResponseEntity postComment(HttpServletRequest req,
                                      @PathVariable Long communityId,
                                      @Valid @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.saveComment(req, communityId, commentRequestDto);
        log.info("Succeeded in posting comment of community : viewer {} => {}", 1, commentResponseDto);
        ResponseData responseData = ResponseData.builder()
                .data(commentResponseDto)
                .build();
        return ResponseEntity.ok().body(responseData);
    }

    @DeleteMapping("/comments/{commentId}")
    @ApiOperation(value = "댓글 삭제")
    public ResponseEntity deleteComment(@PathVariable Long commentId) {
        Long comment = commentService.deleteComment(commentId);
        log.info("Succeeded in deleting comment of community : viewer {} => {}", 1, comment);
        ResponseData responseData = ResponseData.builder()
                .data(comment)
                .build();
        return ResponseEntity.ok().body(responseData);
    }
}
