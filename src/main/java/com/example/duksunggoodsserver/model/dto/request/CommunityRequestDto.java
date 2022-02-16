package com.example.duksunggoodsserver.model.dto.request;

import com.example.duksunggoodsserver.model.entity.Community;
import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.model.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommunityRequestDto {

    @NotBlank
    private String contents;

    private LocalDateTime createdAt = LocalDateTime.now();;

    public Community toCommunityEntity(Item item, User user) {
        return Community.builder()
                .item(item)
                .contents(this.contents)
                .createdAt(this.createdAt)
                .user(user)
                .build();
    }
}
