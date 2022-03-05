package com.example.duksunggoodsserver.model.dto.request;

import com.example.duksunggoodsserver.model.entity.Community;
import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.model.entity.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityRequestDto {

    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public Community toCommunityEntity(Item item, User user) {
        return Community.builder()
                .item(item)
                .contents(this.contents)
                .createdAt(this.createdAt)
                .user(user)
                .build();
    }
}
