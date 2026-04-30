package com.aptech.StickyNoteSQL.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.aptech.StickyNoteSQL.model.Note;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NoteRepository extends ReactiveCrudRepository<Note, Long> {
    Flux<Note> findByOwnerUsername(String ownerUsername);
    Mono<Note> findByIdAndOwnerUsername(Long id, String ownerUsername);
}
