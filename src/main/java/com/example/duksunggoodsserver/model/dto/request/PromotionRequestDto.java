package com.example.duksunggoodsserver.model.dto.request;

import com.example.duksunggoodsserver.model.entity.*;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRequestDto {

    private String image; // TODO: S3 연결

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

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
