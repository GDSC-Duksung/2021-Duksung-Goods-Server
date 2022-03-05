package com.example.duksunggoodsserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/chat")
public class ChatRoomViewController {

    @GetMapping("/")
    public String chatRoomList() {
        return "chatroom";
    }

    @GetMapping("/room/enter/{roomUUID}")
    public String roomDetail(@PathVariable String roomUUID, Model model) {
        model.addAttribute("roomUUID", roomUUID);
        return "roomdetail";
    }
}
