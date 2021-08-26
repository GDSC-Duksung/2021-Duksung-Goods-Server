package com.example.duksunggoodsserver.model.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

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
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Item item;

    @NotNull
    private String contents;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "community", fetch = FetchType.LAZY)
    private List<Comments> commentsList = new ArrayList<Comments>();

}
