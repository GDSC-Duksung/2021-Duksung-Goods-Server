package com.example.duksunggoodsserver.model.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    private String nickname;

    private String phoneNumber;

    private String address;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Community> communityList = new ArrayList<Community>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Like> likeList = new ArrayList<Like>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Item> itemList = new ArrayList<Item>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Buy> buyList = new ArrayList<Buy>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Promotion> promotionList = new ArrayList<Promotion>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Message> messageList = new ArrayList<Message>();
}
