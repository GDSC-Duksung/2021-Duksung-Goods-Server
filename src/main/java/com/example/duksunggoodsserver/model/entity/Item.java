package com.example.duksunggoodsserver.model.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private Integer price;

    private Integer minNumber;

    private Integer maxNumber;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private Boolean progress;

    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @ManyToOne
    private DemandSurveyType demandSurveyType;

}
