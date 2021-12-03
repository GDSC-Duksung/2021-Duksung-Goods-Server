package com.example.duksunggoodsserver.model.dto.response;

import com.example.duksunggoodsserver.model.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long id;

    private String contents;

    private LocalDateTime createdAt;

    private Community community;

    private UserResponseDto user;
}
