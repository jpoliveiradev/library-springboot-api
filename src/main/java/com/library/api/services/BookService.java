package com.library.api.services;

import com.library.api.dtos.book.BookRequestDTO;
import com.library.api.dtos.book.BookResponseDTO;
import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.entities.Book;

public interface BookService {
    Book createBook(BookRequestDTO body);

    PagedResultDTO<BookResponseDTO> getAll(int page, int size);

    BookResponseDTO getById(Long id);
}