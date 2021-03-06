package com.example.duksunggoodsserver.model.dto.request;

import com.example.duksunggoodsserver.model.entity.*;
import com.example.duksunggoodsserver.model.entity.Item;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRequestDto {

    private String image;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public Promotion toPromotionEntity(Item item, User user) {
        return Promotion.builder()
                .item(item)
                .image(this.image)
                .content(this.content)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .user(user)
                .build();
    }
}
