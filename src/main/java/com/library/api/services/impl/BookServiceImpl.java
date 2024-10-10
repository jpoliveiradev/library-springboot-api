package com.library.api.services.impl;

import com.library.api.dtos.book.BookRequestDTO;
import com.library.api.dtos.book.BookResponseDTO;
import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.entities.Book;
import com.library.api.entities.Publisher;
import com.library.api.mappers.BookMapper;
import com.library.api.repositories.BookRepository;
import com.library.api.repositories.PublisherRepository;
import com.library.api.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private BookMapper bookMapper;

    @Override
    public Book createBook(BookRequestDTO data) {
        Publisher publisher = publisherRepository.findById(data.publisher_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editora não encontrada"));

        Book newBook = new Book(data, publisher);
        bookRepository.save(newBook);

        return newBook;
    }

    @Override
    public PagedResultDTO<BookResponseDTO> getAll(int page, int size) {
                Pageable pageable = PageRequest.of(page, size);

        Page<Book> booksPage = this.bookRepository.findAll(pageable);
        Page<BookResponseDTO> booksDTOPage = booksPage.map(bookMapper::mapBookToDTO);

        return new PagedResultDTO<>(
                booksDTOPage.getContent(),
                booksDTOPage.getNumber(),
                booksDTOPage.getSize(),
                booksDTOPage.getTotalElements(),
                booksDTOPage.getTotalPages()
        );
    }

    @Override
    public BookResponseDTO getById(Long id) {
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editora não encontrada"));

        return bookMapper.mapBookToDTO(book);
    }
}