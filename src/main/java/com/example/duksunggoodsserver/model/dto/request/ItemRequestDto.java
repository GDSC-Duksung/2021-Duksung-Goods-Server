package com.example.duksunggoodsserver.model.dto.request;

import com.example.duksunggoodsserver.model.entity.Category;
import com.example.duksunggoodsserver.model.entity.DemandSurveyType;
import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.model.entity.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotNull
    private String description;

    @Min(0)
    private Integer price;

    @Min(1)
    private Integer minNumber;

    @Min(1)
    private Integer maxNumber;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Boolean progress;

    @Min(1)
    @Max(4)
    private Long categoryId;

    @Min(1)
    @Max(2)
    private Long demandSurveyTypeId;

    public Item toItemEntity(User user, Category category, DemandSurveyType demandSurveyType) {
        return Item.builder()
                .title(this.title)
                .description(this.description)
                .price(this.price)
                .minNumber(this.minNumber)
                .maxNumber(this.maxNumber)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .progress(this.progress)
                .category(category)
                .demandSurveyType(demandSurveyType)
                .user(user)
                .build();
    }
}
