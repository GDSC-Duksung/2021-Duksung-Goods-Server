package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.exception.CustomException;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.repository.UserRepository;
import com.example.duksunggoodsserver.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public String signIn(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.createToken(email);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid email/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signUp(User user) {
        if (!userRepository.existsByEmail(user.getEmail()) && !userRepository.existsByNickname(user.getNickname())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            user.setCreatedBy(user.getNickname());
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getEmail());
        } else {
            throw new CustomException("Email or Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public Optional<User> getUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND)));
        return user;
    }

    public Optional<User> getCurrentUser(HttpServletRequest req) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(jwtTokenProvider.getEmail(jwtTokenProvider.resolveToken(req)))
                .orElseThrow(() -> new CustomException("Any user have logged in yet", HttpStatus.BAD_REQUEST)));
        return user;
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username);
    }
}
