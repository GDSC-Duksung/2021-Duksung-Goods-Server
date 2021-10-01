package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.model.entity.Comment;
import com.example.duksunggoodsserver.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/community")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getCommentList(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentList(id));
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Comment> postComment(@PathVariable Long id, @RequestBody Map<String, String> comment) {
        return ResponseEntity.ok(commentService.saveComment(id, comment));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
