package com.example.redunm.service;

import com.example.redunm.model.User;
import com.example.redunm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean isDuplicate(User user) {
        if (findByUsername(user.getUsername()).isPresent()) {
            return true;  // 아이디 중복일 경우 true 반환
        }
        if (findByEmail(user.getEmail()).isPresent()) {
            return true;  // 이메일 중복일 경우 true 반환
        }
        if (findByPhone(user.getPhone()).isPresent()) {
            return true;  // 전화번호 중복일 경우 true 반환
        }
        return false;  // 중복이 없으면 false 반환
    }
}
