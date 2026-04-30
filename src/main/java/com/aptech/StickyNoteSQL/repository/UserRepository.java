package com.aptech.StickyNoteSQL.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.aptech.StickyNoteSQL.model.UserAccount;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserAccount, Long> {
    Mono<UserAccount> findByUsername(String username);
    Mono<UserAccount> findByEmail(String email);
}
