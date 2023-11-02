package com.nmotillon.athena.service;

import com.nmotillon.athena.dto.BookDTO;
import com.nmotillon.athena.dto.CreateOrUpdateBookDTO;
import com.nmotillon.athena.dto.PatchBookDTO;
import com.nmotillon.athena.model.Book;
import com.nmotillon.athena.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    public BookDTO findBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found."));
        return modelMapper.map(book, BookDTO.class);
    }

    public BookDTO updateBook(Long id, CreateOrUpdateBookDTO updateBookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + id));
        modelMapper.map(updateBookDTO, existingBook);
        Book updatedBook = bookRepository.save(existingBook);
        return modelMapper.map(updatedBook, BookDTO.class);
    }

    public BookDTO patchBook(Long bookId, PatchBookDTO patchBookDTO) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + bookId + " not found."));

        // Ignore null properties during the Mapping
        modelMapper.getConfiguration().setPropertyCondition(context -> context.getSource() != null);
        modelMapper.map(patchBookDTO, book);

        Book updatedBook = bookRepository.save(book);
        return modelMapper.map(updatedBook, BookDTO.class);
    }

}
