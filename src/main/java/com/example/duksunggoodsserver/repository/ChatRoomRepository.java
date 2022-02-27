package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.model.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Override
    List<ChatRoom> findAllById(Iterable<Long> longs);
    Optional<ChatRoom> findByRoomUUID(String roomUUID);
}
