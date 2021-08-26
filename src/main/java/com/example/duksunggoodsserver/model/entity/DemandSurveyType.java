package com.example.duksunggoodsserver.model.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandSurveyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @OneToMany(mappedBy = "demandSurveyType", fetch = FetchType.LAZY)
    private List<Item> itemList = new ArrayList<Item>();

}
