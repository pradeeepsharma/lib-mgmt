package com.learning.LibraryManagementSystem.repository;

import com.learning.LibraryManagementSystem.models.BorrowBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowBookRepository extends CrudRepository<BorrowBook, Long> {
}
