package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByUserId(Long id);
    List<Message> findAll();
}
