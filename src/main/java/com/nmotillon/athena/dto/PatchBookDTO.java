package com.nmotillon.athena.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PatchBookDTO {
    private String title;
    private String author;
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN should be either 10 or 13 digits")
    private String isbn;

    public PatchBookDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
