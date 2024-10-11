package com.library.api.dtos.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record BookRequestDTO(@Size(min = 3, max = 50) @NotNull(message = "Campo obrigatório") String name,
                             @Size(min = 3, max = 50) @NotNull(message = "Campo obrigatório") String author,
                             @Min(value = 1500, message = "Ano de lançamento inválido") @NotNull(message = "Campo obrigatório") int releaseYear,
                             @Min(value = 1, message = "Quantidade mínima é 1") @NotNull(message = "Campo obrigatório") int quantity,
                             @NotNull(message = "Campo obrigatório") Long publisher_id) {

    @JsonIgnore
    @AssertTrue(message = "O ano de lançamento não pode ser maior que o ano atual")
    public boolean isValidReleaseYear() {
        return releaseYear <= LocalDate.now().getYear();
    }
}
