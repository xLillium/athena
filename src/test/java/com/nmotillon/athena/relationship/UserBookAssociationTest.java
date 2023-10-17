package com.nmotillon.athena.relationship;

import com.nmotillon.athena.model.Book;
import com.nmotillon.athena.model.User;
import com.nmotillon.athena.repository.BookRepository;
import com.nmotillon.athena.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserBookAssociationTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    private User user1;
    private User user2;
    private Book book1;
    private Book book2;

    @BeforeEach
    public void setup() {
        user1 = new User("Alice", "Alpha");
        user2 = new User("Bob", "Bravo");
        book1 = new Book("1984", "George Orwell", "1234567890");
        book2 = new Book("1985", "George Orwell", "1234567891");

        userRepository.save(user1);
        userRepository.save(user2);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    @Transactional
    public void userShouldBeAbleToHaveABookAssociated() {
        user1.getBooks().add(book1);
        userRepository.save(user1);

        User retrievedUser = userRepository.findById(user1.getId()).orElseThrow();
        assertEquals(1, retrievedUser.getBooks().size());
        assertTrue(retrievedUser.getBooks().contains(book1));
    }

    @Test
    @Transactional
    public void userShouldBeAbleToUpdateAssociatedBooks() {
        user1.getBooks().add(book1);
        userRepository.save(user1);

        user1.getBooks().remove(book1);
        user1.getBooks().add(book2);
        userRepository.save(user1);

        User retrievedUser = userRepository.findById(user1.getId()).orElseThrow();
        assertFalse(retrievedUser.getBooks().contains(book1));
        assertTrue(retrievedUser.getBooks().contains(book2));
    }

    @Test
    @Transactional
    public void userShouldBeAbleToDisassociateABook() {
        user1.getBooks().add(book1);
        userRepository.save(user1);

        user1.getBooks().remove(book1);
        userRepository.save(user1);

        User retrievedUser = userRepository.findById(user1.getId()).orElseThrow();
        assertFalse(retrievedUser.getBooks().contains(book1));
    }

    @Test
    @Transactional
    public void bookShouldBeAbleToRetrieveAssociatedUsers() {
        book1.getOwners().add(user1);
        book1.getOwners().add(user2);
        bookRepository.save(book1);

        Book retrievedBook = bookRepository.findById(book1.getId()).orElseThrow();
        assertEquals(2, retrievedBook.getOwners().size());
        assertTrue(retrievedBook.getOwners().contains(user1));
        assertTrue(retrievedBook.getOwners().contains(user2));
    }

    @Test
    @Transactional
    public void bookShouldBeAbleToUpdateAssociatedUsers() {
        book1.getOwners().add(user1);
        bookRepository.save(book1);

        book1.getOwners().remove(user1);
        book1.getOwners().add(user2);
        bookRepository.save(book1);

        Book retrievedBook = bookRepository.findById(book1.getId()).orElseThrow();
        assertFalse(retrievedBook.getOwners().contains(user1));
        assertTrue(retrievedBook.getOwners().contains(user2));
    }

}
