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

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private Integer price;

    private Integer minNumber;

    private Integer maxNumber;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Boolean progress;

    private Long categoryId;

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
