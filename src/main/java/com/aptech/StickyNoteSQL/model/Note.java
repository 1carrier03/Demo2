package com.aptech.StickyNoteSQL.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("notes")
public class Note {

    @Id
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 2, max = 500, message = "Content must be between 2 and 500 characters")
    private String content;

    private String ownerUsername;

    public Note() {
    }

    public Note(Long id, String title, String content, String ownerUsername) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.ownerUsername = ownerUsername;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Title is required") @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters") String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public @NotBlank(message = "Content is required") @Size(min = 2, max = 500, message = "Content must be between 2 and 500 characters") String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
}
