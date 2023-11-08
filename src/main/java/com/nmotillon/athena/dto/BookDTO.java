package com.nmotillon.athena.dto;

import com.nmotillon.athena.validation.OnCreate;
import com.nmotillon.athena.validation.OnPatch;
import com.nmotillon.athena.validation.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class BookDTO {
    private Long id;
    @NotBlank(message = "Title is mandatory", groups = {OnCreate.class, OnUpdate.class})
    private String title;
    @NotBlank(message = "Author is mandatory", groups = {OnCreate.class, OnUpdate.class})
    private String author;
    @NotBlank(message = "ISBN is mandatory", groups = {OnCreate.class, OnUpdate.class})
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN should be either 10 or 13 digits", groups = {OnCreate.class, OnUpdate.class, OnPatch.class})
    private String isbn;

    public BookDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
