package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.exception.ResourceNotFoundException;
import com.example.duksunggoodsserver.model.dto.request.ChatRequestDto;
import com.example.duksunggoodsserver.model.dto.response.ChatResponseDto;
import com.example.duksunggoodsserver.model.entity.ChatRoom;
import com.example.duksunggoodsserver.model.entity.MessageType;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.repository.ChatRepository;
import com.example.duksunggoodsserver.repository.ChatRoomRepository;
import com.example.duksunggoodsserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<ChatResponseDto> getChatListInChatRoom(String roomUUID) {

        Optional<ChatRoom> chatRoom = Optional.ofNullable(chatRoomRepository.findByRoomUUID(roomUUID)
                .orElseThrow(() -> new ResourceNotFoundException("chatRoom", "roomUUID", roomUUID)));

        List<ChatResponseDto> chatResponseDtoList = chatRepository.findAllByChatRoomId(chatRoom.get().getId())
                .stream().map(message -> {
                    ChatResponseDto chatResponseDto = modelMapper.map(message, ChatResponseDto.class);
                    chatResponseDto.setSender(chatResponseDto.getUser().getNickname());
                    return chatResponseDto;
                })
                .collect(Collectors.toList());
        return chatResponseDtoList;
    }

    @Transactional
    public void saveChat(ChatRequestDto chatRequestDto) {

        Optional<ChatRoom> chatRoom = chatRoomRepository.findByRoomUUID(chatRequestDto.getRoomUUID());
        Optional<User> user = Optional.ofNullable(userRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", 1L))); // TODO: 임시로 해놓음. 추후에 본인 id로 변경

        chatRepository.save(chatRequestDto.toChatEntity(chatRoom.get(), user.get()));
    }

    @Transactional
    public boolean existEnterChat(String roomUUID) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", 1L))); // TODO: 임시로 해놓음. 추후에 본인 id로 변경
        Optional<ChatRoom> chatRoom = Optional.ofNullable(chatRoomRepository.findByRoomUUID(roomUUID)
                .orElseThrow(() -> new ResourceNotFoundException("chatRoom", "roomUUID", roomUUID))); // TODO: 임시로 해놓음. 추후에 본인 id로 변경

        return chatRepository.existsByTypeAndChatRoomIdAndUserId(MessageType.ENTER, chatRoom.get().getId(), user.get().getId());
    }
}
