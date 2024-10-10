package com.library.api.entities;

import com.library.api.dtos.publisher.PublisherRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "publishers")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;
    private String name;
    private String city;

    public Publisher() {
        this.createdAt = LocalDateTime.now();
    }

    public Publisher(PublisherRequestDTO data) {
        this.createdAt = LocalDateTime.now();
        this.name = data.name();
        this.city = data.city();
    }
}
