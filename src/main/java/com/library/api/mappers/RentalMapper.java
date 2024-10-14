package com.library.api.mappers;

import com.library.api.dtos.rentals.RentalResponseDTO;
import com.library.api.entities.Rental;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {
    public RentalResponseDTO mapRentalToDTO(Rental rental) {
        return new RentalResponseDTO(
                rental.getId(),
                rental.getBook().getName(),
                rental.getCustomer().getName(),
                rental.getRentalDate(),
                rental.getForecastDate(),
                rental.getReturnDate()
        );
    }
}
