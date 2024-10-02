package com.library.api.dtos.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record CustomerRequestDTO(@NotEmpty(message = "Campo obrigatório") String name,
                                 @NotEmpty(message = "Campo obrigatório") @Email(message = "E-mail inválido") String email,
                                 @NotEmpty(message = "Campo obrigatório") String address,
                                 @NotEmpty(message = "Campo obrigatória") String city) {
}
