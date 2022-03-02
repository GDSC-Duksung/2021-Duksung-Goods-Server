package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.exception.ResourceNotFoundException;
import com.example.duksunggoodsserver.model.dto.response.ChatRoomJoinResponseDto;
import com.example.duksunggoodsserver.model.dto.response.ChatRoomResponseDto;
import com.example.duksunggoodsserver.model.entity.ChatRoom;
import com.example.duksunggoodsserver.model.entity.ChatRoomJoin;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.repository.ChatRoomJoinRepository;
import com.example.duksunggoodsserver.repository.ChatRoomRepository;
import com.example.duksunggoodsserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Transactional
    public List<ChatRoomResponseDto> getChatRoomList(HttpServletRequest req) {
        Optional<User> user = userService.getCurrentUser(req);
        List<ChatRoomJoin> chatRoomJoinList = chatRoomJoinRepository.findByUser(user.get());
        List<Long> chatRoomIdList = chatRoomJoinList.stream().map(value -> value.getChatRoom().getId()).collect(Collectors.toList());
        List<ChatRoomResponseDto> chatRoomList = chatRoomRepository.findAllById(chatRoomIdList)
                .stream().map(community -> modelMapper.map(community, ChatRoomResponseDto.class))
                .collect(Collectors.toList());
        return chatRoomList;
        // TODO: 채팅방 마지막 업데이트 된 순으로 반환
    }

    @Transactional
    public ChatRoomResponseDto getChatRoom(HttpServletRequest req, String roomUUID) {
        Optional<ChatRoom> chatRoom = Optional.ofNullable(chatRoomRepository.findByRoomUUID(roomUUID)
                .orElseThrow(() -> new ResourceNotFoundException("chatRoom", "chatRoomUUID", roomUUID)));
        return modelMapper.map(chatRoom.get(), ChatRoomResponseDto.class);
    }

    @Transactional
    public ChatRoomResponseDto createChatRoom(HttpServletRequest req, String name, List<Long> userIdList) {
        Optional<User> user = userService.getCurrentUser(req);

        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .roomUUID(UUID.randomUUID().toString())
                .build();
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        ArrayList<ChatRoomJoin> chatRoomJoinArrayList = new ArrayList<>();
        ChatRoomJoin chatRoomJoin = ChatRoomJoin.builder()
                .user(user.get())
                .chatRoom(chatRoom)
                .build();
        chatRoomJoinArrayList.add(chatRoomJoin);
        for (Long userId: userIdList) {
            Optional<User> addUser = Optional.ofNullable(userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId)));

            ChatRoomJoin chatRoomJoin2 = ChatRoomJoin.builder()
                    .user(addUser.get())
                    .chatRoom(chatRoom)
                    .build();
            chatRoomJoinArrayList.add(chatRoomJoin2);
        }
        chatRoomJoinRepository.saveAll(chatRoomJoinArrayList);
        return modelMapper.map(savedChatRoom, ChatRoomResponseDto.class);
    }

    @Transactional
    public ChatRoomJoinResponseDto addUserToChatRoom(HttpServletRequest req, Long chatRoomId, Long userId) {
        Optional<User> user = userService.getCurrentUser(req);
        Optional<ChatRoom> chatRoom = Optional.ofNullable(chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("chatRoom", "chatRoomId", chatRoomId)));

        if (chatRoomJoinRepository.findByUserAndChatRoom(user.get(), chatRoom.get()).isPresent()) {
            // TODO 이미 존재하는 예외 던지기
        }

        ChatRoomJoin chatRoomJoin = ChatRoomJoin.builder()
                .user(user.get())
                .chatRoom(chatRoom.get())
                .build();
        ChatRoomJoin chatRoomJoin2 = chatRoomJoinRepository.save(chatRoomJoin);

        return modelMapper.map(chatRoomJoin2, ChatRoomJoinResponseDto.class);
    }

    @Transactional
    public Long deleteUserFromChatRoom(HttpServletRequest req, Long roomId) {
        Optional<User> user = userService.getCurrentUser(req);
        Optional<ChatRoom> chatRoom = Optional.ofNullable(chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("chatRoom", "chatRoomId", roomId)));
        Optional<ChatRoomJoin> chatRoomJoin = Optional.ofNullable(chatRoomJoinRepository.findByUserAndChatRoom(user.get(), chatRoom.get())
                .orElseThrow(() -> new ResourceNotFoundException("user & chatRoom", "userId & chatRoomId", 1L+" & "+roomId)));
        chatRoomJoinRepository.deleteByUserAndChatRoom(user.get(), chatRoom.get());
        return chatRoomJoin.get().getId();
    }
}
