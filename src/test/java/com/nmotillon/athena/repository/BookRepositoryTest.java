package com.nmotillon.athena.repository;

import com.nmotillon.athena.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
