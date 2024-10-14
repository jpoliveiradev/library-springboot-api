package com.library.api.dtos.rentals;

import java.time.LocalDate;

public record RentalResponseDTO(Long id, String bookName, String customerName, LocalDate rentalDate, LocalDate forecastDate, LocalDate returnDate) {
}
