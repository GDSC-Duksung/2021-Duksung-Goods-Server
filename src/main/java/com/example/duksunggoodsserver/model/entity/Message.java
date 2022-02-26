package com.example.duksunggoodsserver.model.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;

    @NotNull
    private String message;

    @CreatedDate
    private LocalDateTime time;

    @ManyToOne
    private ChatRoom chatRoom;

    @ManyToOne
    private User user;
}
