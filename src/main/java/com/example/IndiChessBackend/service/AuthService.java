package com.example.IndiChessBackend.service;

import com.example.IndiChessBackend.model.User;
import com.example.IndiChessBackend.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public User save(User user) {

        // 🔒 Check duplicate email
        if (userRepo.existsByEmailId(user.getEmailId())) {
            throw new RuntimeException("Email already registered");
        }

        // 🔒 Check duplicate username (if username is unique)
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already taken");
        }

        // 🔐 Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // ⭐ Default values
        user.setRating(250);

        return userRepo.save(user);
    }
}
