package com.library.api.services.impl;

import com.library.api.dtos.SummaryDataDTO;
import com.library.api.dtos.book.BookRequestDTO;
import com.library.api.dtos.book.BookResponseDTO;
import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.entities.Book;
import com.library.api.entities.Publisher;
import com.library.api.mappers.BookMapper;
import com.library.api.repositories.BookRepository;
import com.library.api.repositories.PublisherRepository;
import com.library.api.repositories.RentalRepository;
import com.library.api.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private BookMapper bookMapper;

    @Override
    public Book createBook(BookRequestDTO data) {
        if (this.bookRepository.existsByName(data.name()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Livro já cadastrado");

        Publisher publisher = this.publisherRepository.findById(data.publisher_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editora não encontrada"));

        Book newBook = new Book(data, publisher);
        this.bookRepository.save(newBook);

        return newBook;
    }

    @Override
    public PagedResultDTO<BookResponseDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Book> booksPage = this.bookRepository.findAll(pageable);
        Page<BookResponseDTO> booksDTOPage = booksPage.map(this.bookMapper::mapBookToDTO);

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

        return this.bookMapper.mapBookToDTO(book);
    }

    @Override
    public void updateBook(Long id, BookRequestDTO data) {
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

        Publisher publisher = this.publisherRepository.findById(data.publisher_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editora não encontrada"));

        if (!data.name().equals(book.getName()) && this.bookRepository.existsByName(data.name()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Livro já cadastrado");

        book.setName(data.name());
        book.setAuthor(data.author());
        book.setReleaseYear(data.releaseYear());
        book.setQuantity(data.quantity());
        book.setPublisher(publisher);

        this.bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        if (!this.bookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado");
        }
        if (this.rentalRepository.existsByBookId(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Existem aluguéis cadastrados com esse livro");
        }
        this.bookRepository.deleteById(id);
    }

    @Override
    public List<SummaryDataDTO> getSummaryData() {
       List<Book> books = this.bookRepository.findAll();

       return books.stream().map(this.bookMapper::mapBookToSummaryDataDTO)
               .collect(Collectors.toList());
    }
}
