package com.library.api.dtos.rentals;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RentalRequestCreateDTO(@NotNull(message = "Campo obrigatório") Long book_id,
                                     @NotNull(message = "Campo obrigatório") Long customer_id,
                                     @NotNull(message = "Campo obrigatório") LocalDate rentalDate,
                                     @NotNull(message = "Campo obrigatório") LocalDate forecastDate) {
}
