package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.model.entity.Message;
import com.example.duksunggoodsserver.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    ChatService chatService;

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getMessagesById(@PathVariable Long id) {

        List<Message> messages = chatService.getMessageList(id);
        Map<String, Object> res = new HashMap<>();

        res.put("result", "SUCCESS");
        res.put("data", messages);

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllItems() {

        List<Message> messages = chatService.getAllMessages();
        Map<String, Object> res = new HashMap<>();

        res.put("result", "SUCCESS");
        res.put("data", messages);

        return ResponseEntity.ok().body(res);
    }
}
