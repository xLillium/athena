package com.nmotillon.athena.repository;

import com.nmotillon.athena.model.Book;
import com.nmotillon.athena.model.BorrowRecord;
import com.nmotillon.athena.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class BorrowRecordRepositoryTest {
    @Autowired
    private BorrowRecordRepository borrowRecordRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSaveAndFindBorrowRecord() {
        User lender = new User("LenderFirstName", "LenderLastName");
        userRepository.save(lender);

        User borrower = new User("BorrowerFirstName", "BorrowerLastName");
        userRepository.save(borrower);

        Book book = new Book("Sample Title", "Sample Author", "1234567890123");
        bookRepository.save(book);

        BorrowRecord borrowRecord = new BorrowRecord(borrower, lender, book, LocalDate.now());

        BorrowRecord retrievedRecord = borrowRecordRepository.save(borrowRecord);

        assertNotNull(retrievedRecord.getId());
        assertEquals(borrower.getId(), retrievedRecord.getBorrower().getId());
        assertEquals(lender.getId(), retrievedRecord.getLender().getId());
        assertEquals(book.getId(), retrievedRecord.getBook().getId());
    }

}
