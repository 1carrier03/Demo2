package com.aptech.StickyNoteSQL.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aptech.StickyNoteSQL.model.UserAccount;
import com.aptech.StickyNoteSQL.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<UserAccount> registerUser(UserAccount user) {
        return userRepository.findByUsername(user.getUsername())
                .flatMap(existing -> Mono.<UserAccount>error(
                        new RuntimeException("Username already exists")))
                .switchIfEmpty(
                        userRepository.findByEmail(user.getEmail())
                                .flatMap(existing -> Mono.<UserAccount>error(
                                        new RuntimeException("Email already exists")))
                                .switchIfEmpty(Mono.defer(() -> {
                                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                                    return userRepository.save(user);
                                }))
                );
    }

    public Mono<UserAccount> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
