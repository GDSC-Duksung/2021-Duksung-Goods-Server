package com.example.duksunggoodsserver.model.dto.request;

import com.example.duksunggoodsserver.model.entity.Buy;
import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BuyRequestDto {

    @Min(1)
    private Integer count;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Buy toBuyEntity(Item item, User user) {
        return Buy.builder()
                .item(item)
                .count(this.count)
                .deposit(false)
                .createdAt(this.createdAt)
                .user(user)
                .build();
    }
}