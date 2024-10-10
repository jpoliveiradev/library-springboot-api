package com.library.api.entities;

import com.library.api.dtos.book.BookRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "books")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;
    private String name;
    private String author;
    private int releaseYear;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    private Publisher publisher;

    public Book() {
        this.createdAt = LocalDateTime.now();
    }

    public Book(BookRequestDTO data, Publisher publisher) {
        this.createdAt = LocalDateTime.now();
        this.name = data.name();
        this.author = data.author();
        this.releaseYear = data.releaseYear();
        this.quantity = data.quantity();
        this.publisher = publisher;
    }
}
