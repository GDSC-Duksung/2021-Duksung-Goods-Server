package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    public Optional<User> getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user;
    }
}
