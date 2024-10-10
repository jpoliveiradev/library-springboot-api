package com.library.api.dtos.publisher;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PublisherRequestDTO(@Size(min = 3, max = 50) @NotNull(message = "Campo obrigatório") String name,
                                  @Size(min = 3, max = 30) @NotNull(message = "Campo obrigatória") String city) {
}
