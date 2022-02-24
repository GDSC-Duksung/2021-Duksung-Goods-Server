package com.example.duksunggoodsserver.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityResponseDto {

    private Long id;

    private UserResponseDto user;

    private ItemResponseDto item;

    private String contents;

    private LocalDateTime createdAt;

    private List<CommentResponseDto> comment;
}
