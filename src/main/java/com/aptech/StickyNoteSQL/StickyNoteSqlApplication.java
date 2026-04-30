package com.aptech.StickyNoteSQL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.aptech.StickyNoteSQL","config", "controller", "dto", "model", "repository", "service"})
public class StickyNoteSqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(StickyNoteSqlApplication.class, args);
    }
}