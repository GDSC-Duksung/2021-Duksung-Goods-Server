package com.example.duksunggoodsserver.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/view/chat-rooms")
public class ChatRoomViewController {

    @GetMapping("")
    @ApiOperation(value = "채팅방 리스트 조회 뷰")
    public String chatRoomList() {
        return "chatroom";
    }

    @GetMapping("/{roomUUID}")
    @ApiOperation(value = "채팅 조회 뷰")
    public String roomDetail(@PathVariable String roomUUID, Model model) {
        model.addAttribute("roomUUID", roomUUID);
        return "roomdetail";
    }
}
