package com.nmotillon.athena.service;

import com.nmotillon.athena.dto.BookDTO;
import com.nmotillon.athena.dto.CreateOrUpdateBookDTO;
import com.nmotillon.athena.dto.PatchBookDTO;
import com.nmotillon.athena.model.Book;
import com.nmotillon.athena.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public BookDTO createBook(CreateOrUpdateBookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        Book savedBook = bookRepository.save(book);
        return modelMapper.map(savedBook, BookDTO.class);
    }

    public List<BookDTO> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    public BookDTO findBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + bookId + " not found."));
        return modelMapper.map(book, BookDTO.class);
    }

    @Transactional
    public BookDTO updateBook(Long bookId, CreateOrUpdateBookDTO updateBookDTO) {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + bookId + " not found."));
        modelMapper.map(updateBookDTO, existingBook);
        Book updatedBook = bookRepository.save(existingBook);
        return modelMapper.map(updatedBook, BookDTO.class);
    }

    @Transactional
    public BookDTO patchBook(Long bookId, PatchBookDTO patchBookDTO) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + bookId + " not found."));

        Optional.ofNullable(patchBookDTO.getTitle()).ifPresent(book::setTitle);
        Optional.ofNullable(patchBookDTO.getAuthor()).ifPresent(book::setAuthor);
        Optional.ofNullable(patchBookDTO.getIsbn()).ifPresent(book::setIsbn);

        Book updatedBook = bookRepository.save(book);
        return modelMapper.map(updatedBook, BookDTO.class);
    }

    @Transactional
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + bookId + " not found."));
        bookRepository.delete(book);
    }

}
