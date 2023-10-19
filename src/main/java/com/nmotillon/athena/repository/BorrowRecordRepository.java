package com.nmotillon.athena.repository;

import com.nmotillon.athena.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
}
