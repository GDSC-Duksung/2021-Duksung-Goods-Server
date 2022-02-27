package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.model.entity.ChatRoom;
import com.example.duksunggoodsserver.model.entity.ChatRoomJoin;
import com.example.duksunggoodsserver.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomJoinRepository extends JpaRepository<ChatRoomJoin, Long> {
    List<ChatRoomJoin> findByUser(User user);
    Optional<ChatRoomJoin> deleteByUserAndChatRoom(User user, ChatRoom chatRoom);
}
