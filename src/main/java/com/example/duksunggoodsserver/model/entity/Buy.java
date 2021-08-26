package com.example.duksunggoodsserver.model.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
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

    @NotNull
    private LocalDateTime createdAt;
}