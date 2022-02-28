package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.model.entity.Chat;
import com.example.duksunggoodsserver.model.entity.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByChatRoomId(Long chatRoomId);
    boolean existsByTypeAndChatRoomIdAndUserId(MessageType type, Long chatRoomId, Long userId);
}
