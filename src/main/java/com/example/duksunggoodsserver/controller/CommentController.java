package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.model.dto.response.CommentResponseDto;
import com.example.duksunggoodsserver.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<CommentResponseDto>> getCommentList(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentList(id));
    }

    @PostMapping("/{id}/comment")
    @ApiOperation(value = "댓글 생성")
    public ResponseEntity<CommentResponseDto> postComment(@PathVariable Long id, @RequestBody Map<String, String> comment) {
        return ResponseEntity.ok(commentService.saveComment(id, comment));
    }

    @DeleteMapping("/comment/{id}")
    @ApiOperation(value = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
