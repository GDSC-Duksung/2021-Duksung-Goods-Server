package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.model.dto.response.CommentResponseDto;
import com.example.duksunggoodsserver.model.entity.Comment;
import com.example.duksunggoodsserver.model.entity.Community;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.repository.CommentRepository;
import com.example.duksunggoodsserver.repository.CommunityRepository;
import com.example.duksunggoodsserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<CommentResponseDto> getCommentList(Long id) {

        List<CommentResponseDto> commentResponseDtoList = commentRepository.findAllByCommunityId(id)
                .stream().map(comment -> modelMapper.map(comment, CommentResponseDto.class))
                .collect(Collectors.toList());
        return commentResponseDtoList;
    }

    public CommentResponseDto saveComment(Long id, Map<String, String> comment) {
        Optional<Community> communityId = communityRepository.findById(id);
        Optional<User> userId = userRepository.findById(1L); // TODO: 임시로 해놓음. 추후에 본인 id로 변경

        if (communityId.isPresent() && userId.isPresent()) {
            Comment newComment = commentRepository.save(Comment.builder()
                    .contents(comment.get("contents"))
                    .community(communityId.get())
                    .createdAt(LocalDateTime.now())
                    .user(userId.get())
                    .build());
            return modelMapper.map(newComment, CommentResponseDto.class);
        } else
            return null;
    }

    public Long deleteComment(Long id) {
        if (commentRepository.findById(id).isPresent()) {
            commentRepository.deleteById(id);
            return id;
        } else
            return null;
    }
}





