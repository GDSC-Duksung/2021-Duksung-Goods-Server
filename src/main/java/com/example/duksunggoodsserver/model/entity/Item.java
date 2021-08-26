package com.example.duksunggoodsserver.model.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    private String image;

    @NotNull
    private Integer price;

    private Integer minNumber;

    private Integer maxNumber;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    private Boolean progress;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @ManyToOne
    private DemandSurveyType demandSurveyType;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Community> communityList = new ArrayList<Community>();

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Like> likeList = new ArrayList<Like>();

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Buy> buyList = new ArrayList<Buy>();

    @OneToOne
    private Promotion promotion;
}
