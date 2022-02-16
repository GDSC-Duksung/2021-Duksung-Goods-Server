package com.example.duksunggoodsserver.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIdentityReference(alwaysAsId = true) // 직렬화시 id로만 출력 // TODO 모든 내용 나올 수 있도록
public class CommunityResponseDto {

    private Long id;

    private UserResponseDto user;

    private ItemResponseDto item;

    private String contents;

    private LocalDateTime createdAt;

    private List<CommentResponseDto> comment;
}
