package com.nmotillon.athena.relationship;

import com.nmotillon.athena.model.Book;
import com.nmotillon.athena.model.User;
import com.nmotillon.athena.repository.BookRepository;
import com.nmotillon.athena.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserBookAssociationTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    public void testUserBookAssociation() {
        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        userRepository.save(user);

        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setISBN("9876543210");
        bookRepository.save(book);

        user.getBooks().add(book);
        userRepository.save(user);

        User fetchedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(fetchedUser);
        assertEquals(1, fetchedUser.getBooks().size());
        assertEquals(book.getId(), fetchedUser.getBooks().iterator().next().getId());
    }
}
