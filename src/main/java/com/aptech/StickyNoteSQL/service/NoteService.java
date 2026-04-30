package com.aptech.StickyNoteSQL.service;

import com.aptech.StickyNoteSQL.model.Note;
import com.aptech.StickyNoteSQL.repository.NoteRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Flux<Note> getNotesByOwner(String ownerUsername) {
        return noteRepository.findByOwnerUsername(ownerUsername);
    }

    public Mono<Note> getNoteByIdAndOwner(Long id, String ownerUsername) {
        return noteRepository.findByIdAndOwnerUsername(id, ownerUsername);
    }

    public Mono<Note> save(Note note) {
        return noteRepository.save(note);
    }

    public Mono<Note> update(Long id, String ownerUsername, Note updatedNote) {
        return noteRepository.findByIdAndOwnerUsername(id, ownerUsername)
                .flatMap(existing -> {
                    existing.setTitle(updatedNote.getTitle());
                    existing.setContent(updatedNote.getContent());
                    return noteRepository.save(existing);
                });
    }

    public Mono<Void> delete(Long id, String ownerUsername) {
        return noteRepository.findByIdAndOwnerUsername(id, ownerUsername)
                .flatMap(noteRepository::delete);
    }
}