package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.model.dto.response.ChatResponseDto;
import com.example.duksunggoodsserver.model.entity.Chat;
import com.example.duksunggoodsserver.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<ChatResponseDto> getChatListInChatRoom(Long chatRoomId) {

        List<ChatResponseDto> chatResponseDtoList = chatRepository.findAllByChatRoomId(chatRoomId)
                .stream().map(message -> modelMapper.map(message, ChatResponseDto.class))
                .collect(Collectors.toList());
        return chatResponseDtoList;
    }
}
