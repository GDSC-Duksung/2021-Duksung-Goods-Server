package com.example.duksunggoodsserver.model.dto.response;

import com.example.duksunggoodsserver.model.entity.Category;
import com.example.duksunggoodsserver.model.entity.DemandSurveyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Boolean progress;

    private UserResponseDto user;

    private Category category;

    private DemandSurveyType demandSurveyType;

    private List<ImageResponseDto> imageList;
}
