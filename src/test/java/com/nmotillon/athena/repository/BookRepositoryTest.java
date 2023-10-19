package com.nmotillon.athena.repository;

import com.nmotillon.athena.model.Book;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    public void testSaveAndFindBook() {
        Book book = new Book();
        book.setAuthor("George Orwell");
        book.setTitle("1984");
        book.setIsbn("1234567890");
        bookRepository.save(book);

        Optional<Book> foundBook = bookRepository.findById(book.getId());
        assertTrue(foundBook.isPresent());
        assertEquals("1984", foundBook.get().getTitle());
    }

    @Test
    public void testSaveInvalidBook() {
        Book book = new Book("George Orwell", "1984", "12345");
        assertThrows(ConstraintViolationException.class, () -> bookRepository.save(book));
    }

    @Test
    public void testUniqueIsbnConstraint() {
        Book book1 = new Book("George Orwell", "1984", "1234567890");
        Book book2 = new Book("Aldous Huxley", "Brave New World", "1234567890");

        bookRepository.save(book1);
        assertThrows(DataIntegrityViolationException.class, () -> bookRepository.save(book2));
    }

    @Test
    public void testLongTitleConstraint() {
        String longTitle = "A".repeat(300);
        Book book = new Book("George Orwell", longTitle, "1234567890");

        assertThrows(DataIntegrityViolationException.class, () -> bookRepository.save(book));
    }
}
