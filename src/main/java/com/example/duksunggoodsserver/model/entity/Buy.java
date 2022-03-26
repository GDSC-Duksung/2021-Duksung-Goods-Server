package com.example.duksunggoodsserver.model.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Item item;

    @NotNull
    private Integer count;

    private boolean deposit;

    @NotNull
    private LocalDateTime createdAt;

    private String name;

    private String phoneNumber;

    private String email;

    private String address;

    private Integer zipCode;

    private String extra;

    private String refundAccountNumber;

}