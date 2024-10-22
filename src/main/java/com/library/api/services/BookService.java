package com.library.api.services;

import com.library.api.dtos.SummaryDataDTO;
import com.library.api.dtos.book.BookRequestDTO;
import com.library.api.dtos.book.BookResponseDTO;
import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.entities.Book;

import java.util.List;

public interface BookService {
    Book createBook(BookRequestDTO body);

    PagedResultDTO<BookResponseDTO> getAll(int page, int size);

    BookResponseDTO getById(Long id);

    void updateBook(Long id, BookRequestDTO body);

    void deleteBook(Long id);

    List<SummaryDataDTO> getSummaryData();
}
