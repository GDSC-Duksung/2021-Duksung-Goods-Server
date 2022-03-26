package com.example.duksunggoodsserver.model.dto.request;

import com.example.duksunggoodsserver.model.entity.Buy;
import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.model.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
@Getter
@NoArgsConstructor
public class BuyRequestDto {

    @Min(1)
    private Integer count;

    private LocalDateTime createdAt = LocalDateTime.now();

    private String name;

    private String phoneNumber;

    private String email;

    private String address;

    private Integer zipCode;

    private String extra;

    private String refundAccountNumber;

    public Buy toBuyEntity(Item item, User user) {
        return Buy.builder()
                .item(item)
                .count(this.count)
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .email(this.email)
                .address(this.address)
                .zipCode(this.zipCode)
                .extra(this.extra)
                .refundAccountNumber(this.refundAccountNumber)
                .deposit(false)
                .createdAt(this.createdAt)
                .user(user)
                .build();
    }
}