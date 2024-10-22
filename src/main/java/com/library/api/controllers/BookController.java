package com.library.api.controllers;

import com.library.api.dtos.SummaryDataDTO;
import com.library.api.dtos.book.BookRequestDTO;
import com.library.api.dtos.book.BookResponseDTO;
import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.entities.Book;
import com.library.api.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @Operation(summary = "Create book")
    public ResponseEntity<Book> create(@RequestBody @Valid BookRequestDTO body) {
        Book book = this.bookService.createBook(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @GetMapping
    @Operation(summary = "Get all books")
    public ResponseEntity<PagedResultDTO<BookResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PagedResultDTO<BookResponseDTO> allBooks = this.bookService.getAll(page, size);
        return ResponseEntity.ok(allBooks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id")
    public ResponseEntity<BookResponseDTO> getById(@PathVariable Long id) {
        BookResponseDTO book = this.bookService.getById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/summary-data")
    @Operation(summary = "get summary data of books")
    public ResponseEntity<List<SummaryDataDTO>> getSummaryData() {
        List<SummaryDataDTO> books = this.bookService.getSummaryData();
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody BookRequestDTO body) {
        this.bookService.updateBook(id, body);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
