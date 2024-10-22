package com.library.api.mappers;

import com.library.api.dtos.book.BookResponseDTO;
import com.library.api.entities.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public BookResponseDTO mapBookToDTO(Book book  ) {
        return new BookResponseDTO(
                book.getId(),
                book.getName(),
                book.getAuthor(),
                book.getReleaseYear(),
                book.getQuantity(),
                book.getPublisher().getName()
        );
    }
}
