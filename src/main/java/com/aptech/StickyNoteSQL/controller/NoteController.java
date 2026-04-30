package com.aptech.StickyNoteSQL.controller;

import com.aptech.StickyNoteSQL.model.Note;
import com.aptech.StickyNoteSQL.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public Mono<String> viewNotes(Authentication authentication, Model model) {
        String username = authentication.getName();

        return noteService.getNotesByOwner(username)
                .collectList()
                .doOnNext(notes -> {
                    model.addAttribute("notes", notes);
                    model.addAttribute("username", username);
                })
                .thenReturn("viewnotes");
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("note", new Note());
        model.addAttribute("editMode", false);
        return "addnotes";
    }

    @PostMapping("/save")
    public Mono<String> saveNote(@Valid @ModelAttribute("note") Note note,
                                 BindingResult bindingResult,
                                 Authentication authentication,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", false);
            return Mono.just("addnotes");
        }

        note.setOwnerUsername(authentication.getName());
        return noteService.save(note).thenReturn("redirect:/notes");
    }

    @GetMapping("/edit/{id}")
    public Mono<String> showEditForm(@PathVariable("id") Long id,
                                     Authentication authentication,
                                     Model model) {
        return noteService.getNoteByIdAndOwner(id, authentication.getName())
                .map(note -> {
                    model.addAttribute("note", note);
                    model.addAttribute("editMode", true);
                    return "addnotes";
                })
                .switchIfEmpty(Mono.just("redirect:/notes"));
    }

    @PostMapping("/update/{id}")
    public Mono<String> updateNote(@PathVariable("id") Long id,
                                   @Valid @ModelAttribute("note") Note note,
                                   BindingResult bindingResult,
                                   Authentication authentication,
                                   Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", true);
            note.setId(id);
            model.addAttribute("note", note);
            return Mono.just("addnotes");
        }

        return noteService.update(id, authentication.getName(), note)
                .switchIfEmpty(Mono.just(note))
                .thenReturn("redirect:/notes");
    }

    @PostMapping("/delete/{id}")
    public Mono<String> deleteNote(@PathVariable("id") Long id,
                                   Authentication authentication) {
        return noteService.delete(id, authentication.getName())
                .thenReturn("redirect:/notes");
    }
}