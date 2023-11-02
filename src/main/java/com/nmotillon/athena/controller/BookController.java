package com.nmotillon.athena.controller;

import com.nmotillon.athena.dto.BookDTO;
import com.nmotillon.athena.dto.CreateOrUpdateBookDTO;
import com.nmotillon.athena.dto.PatchBookDTO;
import com.nmotillon.athena.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public BookDTO createBook(@Valid @RequestBody CreateOrUpdateBookDTO bookDTO) {
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
    public BookDTO updateBook(@PathVariable Long bookId,
                                              @Valid @RequestBody CreateOrUpdateBookDTO updateBookDTO) {
        return bookService.updateBook(bookId, updateBookDTO);
    }

    @PatchMapping("/{bookId}")
    public BookDTO patchBook(@PathVariable Long bookId, @Valid @RequestBody PatchBookDTO patchBookDTO) {
        return bookService.patchBook(bookId, patchBookDTO);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

}
