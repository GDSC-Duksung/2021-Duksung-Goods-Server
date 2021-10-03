package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.model.entity.Message;
import com.example.duksunggoodsserver.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    public List<Message> getMessageList(Long id) {
        List<Message> message = chatRepository.findAllByUserId(id);
        return message;
    }

    public List<Message> getAllMessages() { return chatRepository.findAll(); }
}
