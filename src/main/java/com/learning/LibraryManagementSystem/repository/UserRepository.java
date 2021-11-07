package com.learning.LibraryManagementSystem.repository;

import com.learning.LibraryManagementSystem.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
