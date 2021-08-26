package com.example.duksunggoodsserver.model.entity;

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
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    private List<Message> messageList = new ArrayList<Message>();

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    private List<ChatRoomJoin> chatRoomJoinList = new ArrayList<ChatRoomJoin>();
}
