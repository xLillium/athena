package com.nmotillon.athena.validation;

import com.nmotillon.athena.model.Book;
import com.nmotillon.athena.model.BorrowRecord;
import com.nmotillon.athena.model.User;
import com.nmotillon.athena.repository.BookRepository;
import com.nmotillon.athena.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BorrowRecordValidationTest {
    @Autowired
    private LocalValidatorFactoryBean validator;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    private BorrowRecord borrowRecord;

    @BeforeEach
    public void setUp() {
        User lender = new User("LenderFirstName", "LenderLastName");
        userRepository.save(lender);

        User borrower = new User("BorrowerFirstName", "BorrowerLastName");
        userRepository.save(borrower);

        Book book = new Book("Sample Title", "Sample Author", "1234567890123");
        bookRepository.save(book);

        borrowRecord = new BorrowRecord(borrower, lender, book, LocalDate.now());
    }

    @Test
    public void whenValidData_thenNoViolations() {
        Set<ConstraintViolation<BorrowRecord>> violations = validator.validate(borrowRecord);
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenNoBorrower_thenViolation() {
        borrowRecord.setBorrower(null);
        Set<ConstraintViolation<BorrowRecord>> violations = validator.validate(borrowRecord);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("Borrower is mandatory");
    }

    @Test
    public void whenNoBook_thenViolation() {
        borrowRecord.setBook(null);
        Set<ConstraintViolation<BorrowRecord>> violations = validator.validate(borrowRecord);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("Book is mandatory");
    }
}
