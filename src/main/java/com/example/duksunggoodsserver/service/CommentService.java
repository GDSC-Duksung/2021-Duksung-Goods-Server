package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.model.entity.Comment;
import com.example.duksunggoodsserver.model.entity.Community;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.repository.CommentRepository;
import com.example.duksunggoodsserver.repository.CommunityRepository;
import com.example.duksunggoodsserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommunityRepository communityRepository;
    @Autowired
    UserRepository userRepository;

    public List<Comment> getCommentList(Long id) {
        List<Comment> comment = commentRepository.findAllByCommunityId(id);
        return comment;
    }

    public void saveComment(Long id, Map<String, String> comment) {
        Optional<Community> communityId =
                communityRepository.findById(id);
        Optional<User> userId = userRepository.findById(1L); // TODO: 임시로 해놓음. 추후에 본인 id로 변경
        commentRepository.save(Comment.builder()
                .contents(comment.get("contents"))
                .community(communityId.get())
                .createdAt(LocalDateTime.now())
                .user(userId.get())
                .build());
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}





