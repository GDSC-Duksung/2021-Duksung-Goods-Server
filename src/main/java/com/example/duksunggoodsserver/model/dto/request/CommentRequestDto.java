package com.example.duksunggoodsserver.model.dto.request;

import com.example.duksunggoodsserver.model.entity.Comment;
import com.example.duksunggoodsserver.model.entity.Community;
import com.example.duksunggoodsserver.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "댓글을 입력해주세요.")
    private String contents;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Comment toCommentEntity(Community community, User user) {
        return Comment.builder()
                .community(community)
                .contents(this.contents)
                .createdAt(this.createdAt)
                .user(user)
                .build();
    }
}