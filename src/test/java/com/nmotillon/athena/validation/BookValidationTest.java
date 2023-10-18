package com.nmotillon.athena.validation;

import com.nmotillon.athena.model.Book;
import com.nmotillon.athena.repository.BookRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class BookValidationTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    public void savingBookWithNullTitleShouldThrowValidationException() {
        Book book = new Book(null, "Sample Author", "1234567890");
        assertThrows(ConstraintViolationException.class, () -> bookRepository.save(book));
    }

    @Test
    public void savingBookWithNullAuthorShouldThrowValidationException() {
        Book book = new Book("Sample Title", null, "1234567890");
        assertThrows(ConstraintViolationException.class, () -> bookRepository.save(book));
    }

    @Test
    public void savingBookWithNullIsbnShouldThrowValidationException() {
        Book book = new Book("Sample Title", "Sample Author", null);
        assertThrows(ConstraintViolationException.class, () -> bookRepository.save(book));
    }

    @Test
    public void savingBookWithIsbnLengthOf10_ShouldBeValid() {
        Book book = new Book("Sample Title", "Sample Author", "1234567890");
        Book savedBook = bookRepository.save(book);

        Optional<Book> retrievedBook = bookRepository.findById(savedBook.getId());

        assertTrue(retrievedBook.isPresent());
        assertEquals("1234567890", retrievedBook.get().getIsbn());
    }

    @Test
    public void savingBookWithIsbnLengthOf13_ShouldBeValid() {
        Book book = new Book("Sample Title", "Sample Author", "1234567890123");
        Book savedBook = bookRepository.save(book);

        Optional<Book> retrievedBook = bookRepository.findById(savedBook.getId());

        assertTrue(retrievedBook.isPresent());
        assertEquals("1234567890123", retrievedBook.get().getIsbn());
    }

    @Test
    public void savingBookWithIsbnLengthOf11_ShouldThrowValidationException() {
        Book book = new Book("Sample Title", "Sample Author", "12345678901");
        // Expect a validation exception to be thrown
        assertThrows(ConstraintViolationException.class, () -> bookRepository.save(book));
    }

    @Test
    public void savingBookWithIsbnLengthOf12_ShouldThrowValidationException() {
        Book book = new Book("Sample Title", "Sample Author", "123456789012");
        // Expect a validation exception to be thrown
        assertThrows(ConstraintViolationException.class, () -> bookRepository.save(book));
    }

}
