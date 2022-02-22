package com.example.duksunggoodsserver.model.dto.response;

import com.example.duksunggoodsserver.model.entity.Category;
import com.example.duksunggoodsserver.model.entity.DemandSurveyType;
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
public class ItemResponseDto {

    private Long id;

    private String title;

    private String description;

    private Integer price;

    private Integer minNumber;

    private Integer maxNumber;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean progress;

    private UserResponseDto user;

    private Category category;

    private DemandSurveyType demandSurveyType;

    private List<ImageResponseDto> imageList;
}
