package com.learning.LibraryManagementSystem.repository;

import com.learning.LibraryManagementSystem.models.Billing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends CrudRepository<Billing, Long> {
}
