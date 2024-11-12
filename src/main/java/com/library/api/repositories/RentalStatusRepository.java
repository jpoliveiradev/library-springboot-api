package com.library.api.repositories;

import com.library.api.entities.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalStatusRepository extends JpaRepository<RentalStatus, Long> {
}
