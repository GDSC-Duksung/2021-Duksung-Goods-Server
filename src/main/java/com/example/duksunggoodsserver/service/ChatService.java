package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.exception.ResourceNotFoundException;
import com.example.duksunggoodsserver.model.dto.request.ChatRequestDto;
import com.example.duksunggoodsserver.model.dto.response.ChatResponseDto;
import com.example.duksunggoodsserver.model.entity.ChatRoom;
import com.example.duksunggoodsserver.model.entity.MessageType;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.repository.ChatRepository;
import com.example.duksunggoodsserver.repository.ChatRoomJoinRepository;
import com.example.duksunggoodsserver.repository.ChatRoomRepository;
import com.example.duksunggoodsserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Transactional
    public List<ChatResponseDto> getChatListInChatRoom(HttpServletRequest req, String roomUUID) {
        Optional<User> user = userService.getCurrentUser(req);
        Optional<ChatRoom> chatRoom = Optional.ofNullable(chatRoomRepository.findByRoomUUID(roomUUID)
                .orElseThrow(() -> new ResourceNotFoundException("chatRoom", "roomUUID", roomUUID)));

        LocalDateTime enterTime = chatRoomJoinRepository.findByUserAndChatRoom(user.get(), chatRoom.get()).get().getCreatedAt();
        List<ChatResponseDto> chatResponseDtoList = chatRepository.findAllByChatRoomId(chatRoom.get().getId())
                .stream()
                .filter(message -> message.getTime().isAfter(enterTime)) // 들어오기 이전 메시지는 보지 못함
                .map(message -> {
                    ChatResponseDto chatResponseDto = modelMapper.map(message, ChatResponseDto.class);
                    chatResponseDto.setSender(chatResponseDto.getUser().getNickname());
                    return chatResponseDto;
                })
                .collect(Collectors.toList());
        return chatResponseDtoList;
    }

    @Transactional
    public void saveChat(ChatRequestDto chatRequestDto) {
        Optional<User> user = userRepository.findById(chatRequestDto.getSenderId());
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByRoomUUID(chatRequestDto.getRoomUUID());
        chatRepository.save(chatRequestDto.toChatEntity(chatRoom.get(), user.get()));
    }

    @Transactional
    public boolean existEnterChat(Long senderId, String roomUUID) {
        Optional<User> user = userRepository.findById(senderId);
        Optional<ChatRoom> chatRoom = Optional.ofNullable(chatRoomRepository.findByRoomUUID(roomUUID)
                .orElseThrow(() -> new ResourceNotFoundException("chatRoom", "roomUUID", roomUUID)));
        return chatRepository.existsByTypeAndChatRoomIdAndUserId(MessageType.ENTER, chatRoom.get().getId(), user.get().getId());
    }
}
