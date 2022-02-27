package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.exception.ResourceNotFoundException;
import com.example.duksunggoodsserver.model.dto.response.UserResponseDto;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public UserResponseDto getUser() {
        Optional<User> user = Optional.ofNullable(userRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", 1L))); // TODO: 임시로 해놓음. 추후에 본인 id로 변경

        return modelMapper.map(user.get(), UserResponseDto.class);
    }

}
