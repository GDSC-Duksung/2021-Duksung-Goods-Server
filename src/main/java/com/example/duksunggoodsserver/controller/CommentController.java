package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.model.entity.Comment;
import com.example.duksunggoodsserver.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Comment> getCommentList(@PathVariable Long id) {
        List<Comment> commentList = commentService.getCommentList(id);
        return commentList;
    }

    @PostMapping("/{id}/comment")
    public void postComment(@PathVariable Long id, @RequestBody Map<String, String> comment) {
        commentService.saveComment(id, comment);
    }

    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        // TODO: null일 때 처리
    }
}
