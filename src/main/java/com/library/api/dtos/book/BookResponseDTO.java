package com.library.api.dtos.book;

import com.library.api.entities.Publisher;

public record BookResponseDTO(Long id, String name, String author, int releaseYear, int quantity, Publisher publisher) {
}
