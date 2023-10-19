package com.nmotillon.athena.validation;

import com.nmotillon.athena.model.Book;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class BookValidationTest {
    @Autowired
    private LocalValidatorFactoryBean validator;
    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book("Sample Title", "Sample Author", "1234567890123");
    }

    @Test
    public void whenValidData_thenNoViolations() {
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenNullTitle_thenViolation() {
        book.setTitle(null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("Title is mandatory");
    }

    @Test
    public void whenNullAuthor_thenViolation() {
        book.setAuthor(null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("Author is mandatory");
    }

    @Test
    public void whenNullIsbn_thenViolation() {
        book.setIsbn(null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("ISBN is mandatory");
    }

    @Test
    public void whenIsbnLengthOf10_thenValid() {
        book.setIsbn("1234567890");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenIsbnLengthOf13_thenValid() {
        book.setIsbn("1234567890123");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenIsbnLengthOf11_thenViolation() {
        book.setIsbn("12345678901");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("ISBN should be either 10 or 13 digits");
    }

    @Test
    public void whenIsbnLengthOf12_thenViolation() {
        book.setIsbn("123456789012");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("ISBN should be either 10 or 13 digits");
    }

}
