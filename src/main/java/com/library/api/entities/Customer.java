package com.library.api.entities;

import com.library.api.dtos.customer.CustomerRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "customers")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;
    private String name;
    private String email;
    private String address;
    private String city;

    public Customer() {
        this.createdAt = LocalDateTime.now();
    }

    public Customer(CustomerRequestDTO data) {
        this.createdAt = LocalDateTime.now();
        this.name = data.name();
        this.email = data.email();
        this.address = data.address();
        this.city = data.city();
    }
}
