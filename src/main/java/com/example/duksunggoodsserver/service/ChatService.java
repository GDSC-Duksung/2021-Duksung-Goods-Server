package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.model.dto.response.MessageResponseDto;
import com.example.duksunggoodsserver.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ModelMapper modelMapper;

    public List<MessageResponseDto> getMessageList(Long id) {

        List<MessageResponseDto> messageResponseDtoList = chatRepository.findAllByUserId(id)
                .stream().map(message -> modelMapper.map(message, MessageResponseDto.class))
                .collect(Collectors.toList());
        return messageResponseDtoList;
    }

    public List<MessageResponseDto> getAllMessages() {

        List<MessageResponseDto> messageResponseDtoList = chatRepository.findAll()
                .stream().map(message -> modelMapper.map(message, MessageResponseDto.class))
                .collect(Collectors.toList());
        return messageResponseDtoList;
    }
}
