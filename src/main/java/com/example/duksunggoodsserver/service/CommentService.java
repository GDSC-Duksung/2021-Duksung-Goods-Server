package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.exception.ResourceNotFoundException;
import com.example.duksunggoodsserver.model.dto.request.CommentRequestDto;
import com.example.duksunggoodsserver.model.dto.response.CommentResponseDto;
import com.example.duksunggoodsserver.model.entity.Comment;
import com.example.duksunggoodsserver.model.entity.Community;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.repository.CommentRepository;
import com.example.duksunggoodsserver.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Transactional
    public List<CommentResponseDto> getCommentList(Long id) {
        List<CommentResponseDto> commentResponseDtoList = commentRepository.findAllByCommunityId(id)
                .stream().map(comment -> modelMapper.map(comment, CommentResponseDto.class))
                .collect(Collectors.toList());
        return commentResponseDtoList;
    }

    @Transactional
    public CommentResponseDto saveComment(HttpServletRequest req, Long id, CommentRequestDto commentRequestDto) {
        Optional<User> user = userService.getCurrentUser(req);
        Optional<Community> community = Optional.ofNullable(communityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("community", "communityId", id)));
        Comment newComment = commentRepository.save(commentRequestDto.toCommentEntity(community.get(), user.get()));
        return modelMapper.map(newComment, CommentResponseDto.class);
    }

    @Transactional
    public Long deleteComment(Long id) {
        Optional.ofNullable(commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("comment", "commentId", id)));
        commentRepository.deleteById(id);
        return id;
    }
}





