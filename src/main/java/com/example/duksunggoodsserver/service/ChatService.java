package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.exception.ResourceNotFoundException;
import com.example.duksunggoodsserver.model.dto.response.MessageResponseDto;
import com.example.duksunggoodsserver.model.entity.ChatRoom;
import com.example.duksunggoodsserver.model.entity.ChatRoomJoin;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.repository.ChatRepository;
import com.example.duksunggoodsserver.repository.ChatRoomJoinRepository;
import com.example.duksunggoodsserver.repository.ChatRoomRepository;
import com.example.duksunggoodsserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<ChatRoom> getChatRoomList() {

        Optional<User> user = Optional.ofNullable(userRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", 1L))); // TODO: 임시로 해놓음. 추후에 본인 id로 변경
        List<ChatRoomJoin> chatRoomJoinList = chatRoomJoinRepository.findByUser(user.get());
        List<Long> list = chatRoomJoinList.stream().map(value -> value.getChatRoom().getId()).collect(Collectors.toList());
        return chatRoomRepository.findAllById(list);
        // TODO: 채팅방 마지막 업데이트 된 순으로 반환
    }

    @Transactional
    public ChatRoom getChatRoom(String roomUUID) {
        return chatRoomRepository.findByRoomUUID(roomUUID).get();
    }

    @Transactional
    public ChatRoom createChatRoomWithOne(String name) {

        Optional<User> user = Optional.ofNullable(userRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", 1L))); // TODO: 임시로 해놓음. 추후에 본인 id로 변경

        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .roomUUID(UUID.randomUUID().toString())
                .build();
        chatRoomRepository.save(chatRoom);

        ChatRoomJoin chatRoomJoin = ChatRoomJoin.builder()
                .user(user.get())
                .chatRoom(chatRoom)
                .build();
        chatRoomJoinRepository.save(chatRoomJoin);

        return chatRoom;
    }

    @Transactional
    public ChatRoom createChatRoomWithMany(String name, List<Long> userIdList) {

        Optional<User> user = Optional.ofNullable(userRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", 1L))); // TODO: 임시로 해놓음. 추후에 본인 id로 변경

        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .roomUUID(UUID.randomUUID().toString())
                .build();
        chatRoomRepository.save(chatRoom);

        ChatRoomJoin chatRoomJoin = ChatRoomJoin.builder()
                .user(user.get())
                .chatRoom(chatRoom)
                .build();
        chatRoomJoinRepository.save(chatRoomJoin);

        ArrayList<ChatRoomJoin> chatRoomJoinArrayList = new ArrayList<>();
        for (Long userId: userIdList) {
            Optional<User> addUser = Optional.ofNullable(userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId))); // TODO: 임시로 해놓음. 추후에 본인 id로 변경

            ChatRoomJoin chatRoomJoin2 = ChatRoomJoin.builder()
                    .user(addUser.get())
                    .chatRoom(chatRoom)
                    .build();
            chatRoomJoinArrayList.add(chatRoomJoin2);
        }
        chatRoomJoinRepository.saveAll(chatRoomJoinArrayList);
        return chatRoom;
    }

    @Transactional
    public ChatRoomJoin addUserToChatRoom(Long chatRoomId, Long userId) {

        Optional<User> user = Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId))); // TODO: 임시로 해놓음. 추후에 본인 id로 변경
        Optional<ChatRoom> chatRoom = Optional.ofNullable(chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("chatRoom", "chatRoomId", chatRoomId)));

        ChatRoomJoin chatRoomJoin = ChatRoomJoin.builder()
                .user(user.get())
                .chatRoom(chatRoom.get())
                .build();
        chatRoomJoinRepository.save(chatRoomJoin);

        return chatRoomJoin;
    }

    @Transactional
    public Optional<ChatRoomJoin> deleteUserFromChatRoom(Long roomId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", 1L))); // TODO: 임시로 해놓음. 추후에 본인 id로 변경
        Optional<ChatRoom> chatRoom = Optional.ofNullable(chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("chatRoom", "chatRoomId", roomId)));

        return chatRoomJoinRepository.deleteByUserAndChatRoom(user.get(), chatRoom.get());
    }

    @Transactional
    public List<MessageResponseDto> getMessageList(Long id) {

        List<MessageResponseDto> messageResponseDtoList = chatRepository.findAllByUserId(id)
                .stream().map(message -> modelMapper.map(message, MessageResponseDto.class))
                .collect(Collectors.toList());
        return messageResponseDtoList;
    }

    @Transactional
    public List<MessageResponseDto> getAllMessages() {

        List<MessageResponseDto> messageResponseDtoList = chatRepository.findAll()
                .stream().map(message -> modelMapper.map(message, MessageResponseDto.class))
                .collect(Collectors.toList());
        return messageResponseDtoList;
    }
}
