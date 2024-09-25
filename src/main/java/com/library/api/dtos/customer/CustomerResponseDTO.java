package com.library.api.dtos.customer;

import java.time.LocalDateTime;

public record CustomerResponseDTO(Long id, LocalDateTime createdAt, String name, String email, String address, String city) {
}
