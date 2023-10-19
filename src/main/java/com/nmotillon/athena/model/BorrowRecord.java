package com.nmotillon.athena.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrow_records")
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull(message = "Book is mandatory")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "lender_id", nullable = false)
    @NotNull(message = "Lender is mandatory")
    private User lender;

    @ManyToOne
    @JoinColumn(name = "borrower_id", nullable = false)
    @NotNull(message = "Borrower is mandatory")
    private User borrower;

    @Column(name = "borrowed_at", nullable = false)
    @NotNull
    private LocalDateTime borrowedAt;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    public BorrowRecord(User borrower, User lender, Book book, LocalDate now) {
        this.borrower = borrower;
        this.lender = lender;
        this.book = book;
        this.borrowedAt = now.atStartOfDay();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getLender() {
        return lender;
    }

    public void setLender(User lender) {
        this.lender = lender;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public void setBorrowedAt(LocalDateTime borrowedAt) {
        this.borrowedAt = borrowedAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
