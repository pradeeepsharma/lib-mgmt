package com.learning.LibraryManagementSystem.repository;

import com.learning.LibraryManagementSystem.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book,Long> {
}
