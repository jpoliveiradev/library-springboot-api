package com.library.api.repositories;

import com.library.api.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RentalRepository extends JpaRepository<Rental, Long>, JpaSpecificationExecutor<Rental> {
    boolean existsByBookId(Long bookId);
    boolean existsByCustomerId(Long customerId);
}
