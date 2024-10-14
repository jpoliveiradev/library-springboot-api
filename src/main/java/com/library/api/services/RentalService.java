package com.library.api.services;

import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.dtos.rentals.RentalRequestCreateDTO;
import com.library.api.dtos.rentals.RentalResponseDTO;
import com.library.api.entities.Rental;

public interface RentalService {
    Rental createRental(RentalRequestCreateDTO body);

    PagedResultDTO<RentalResponseDTO> getAll(int page, int size);

    RentalResponseDTO getById(Long id);
}
