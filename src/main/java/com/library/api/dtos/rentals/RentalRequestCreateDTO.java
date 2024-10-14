package com.library.api.dtos.rentals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RentalRequestCreateDTO(@NotNull(message = "Campo obrigatório") Long book_id,
                                     @NotNull(message = "Campo obrigatório") Long customer_id,
                                     @NotNull(message = "Campo obrigatório") LocalDate rentalDate,
                                     @NotNull(message = "Campo obrigatório") LocalDate forecastDate) {

    @JsonIgnore
    @AssertTrue(message = "A data de previsão não pode ser anterior à data de aluguel")
    public boolean isForecastDateValid() {
        return forecastDate.isAfter(rentalDate) || forecastDate.isEqual(rentalDate);
    }

    @JsonIgnore
    @AssertTrue(message = "A data de aluguel não pode ser anterior à data atual")
    public boolean isRentalDateValid() {
        return rentalDate.isAfter(LocalDate.now()) || rentalDate.isEqual(LocalDate.now());
    }
}
