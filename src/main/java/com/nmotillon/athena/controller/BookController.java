package com.nmotillon.athena.controller;

import com.nmotillon.athena.dto.BookDTO;
import com.nmotillon.athena.service.BookService;
import com.nmotillon.athena.validation.OnCreate;
import com.nmotillon.athena.validation.OnPatch;
import com.nmotillon.athena.validation.OnUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook(@Validated(OnCreate.class) @RequestBody BookDTO bookDTO) {
        return bookService.createBook(bookDTO);
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{bookId}")
    public BookDTO getBookById(@PathVariable Long bookId) {
        return bookService.findBookById(bookId);
    }

    @PutMapping("/{bookId}")
    public BookDTO updateBook(@PathVariable Long bookId, @Validated(OnUpdate.class) @RequestBody BookDTO bookDTO) {
        return bookService.updateBook(bookId, bookDTO);
    }

    @PatchMapping("/{bookId}")
    public BookDTO patchBook(@PathVariable Long bookId, @Validated(OnPatch.class) @RequestBody BookDTO bookDTO) {
        return bookService.updateBook(bookId, bookDTO);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

}
