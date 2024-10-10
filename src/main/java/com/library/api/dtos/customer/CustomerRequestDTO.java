package com.library.api.dtos.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerRequestDTO(@Size(min = 3, max = 100) @NotNull(message = "Campo obrigatório") String name,
                                 @NotNull(message = "Campo obrigatório") @Email(message = "E-mail inválido") String email,
                                 @Size(min = 3, max = 100) @NotNull(message = "Campo obrigatório")  String address,
                                 @Size(min = 3, max = 30) @NotNull(message = "Campo obrigatório")  String city) {
}
