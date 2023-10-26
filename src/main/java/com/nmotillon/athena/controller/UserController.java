package com.nmotillon.athena.controller;

import com.nmotillon.athena.model.Book;
import com.nmotillon.athena.model.User;
import com.nmotillon.athena.repository.BookRepository;
import com.nmotillon.athena.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public UserController(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping()
    List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found."));
    }

    @GetMapping("/{userId}/books")
    public List<Book> getBooksByUser(@PathVariable Long userId) {
        return bookRepository.findByOwners_Id(userId);
    }
}
