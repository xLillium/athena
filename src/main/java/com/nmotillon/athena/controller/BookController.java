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
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody CreateOrUpdateBookDTO bookDTO) {
        BookDTO newBook = bookService.createBook(bookDTO);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long bookId) {
        BookDTO bookDTO = bookService.findBookById(bookId);
        return ResponseEntity.ok(bookDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id,
                                              @Valid @RequestBody CreateOrUpdateBookDTO updateBookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, updateBookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<BookDTO> patchBook(@PathVariable Long bookId, @Valid @RequestBody PatchBookDTO bookPartialUpdateDTO) {
        BookDTO updatedBook = bookService.patchBook(bookId, bookPartialUpdateDTO);
        return ResponseEntity.ok(updatedBook);
    }


}
