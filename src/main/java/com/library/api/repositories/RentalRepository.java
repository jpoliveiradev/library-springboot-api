package com.library.api.repositories;

import com.library.api.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    boolean existsByBookId(Long bookId);
    boolean existsByCustomerId(Long customerId);
}
