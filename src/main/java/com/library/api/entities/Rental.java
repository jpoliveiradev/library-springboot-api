package com.library.api.entities;

import com.library.api.dtos.rentals.RentalRequestCreateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "rentals")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;
    private LocalDate rentalDate;
    private LocalDate forecastDate;
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private RentalStatus status;

    public Rental() {
        this.createdAt = LocalDateTime.now();
    }

    public Rental(RentalRequestCreateDTO data, Book book, Customer customer, RentalStatus rentalStatus) {
        this.createdAt = LocalDateTime.now();
        this.book = book;
        this.customer = customer;
        this.rentalDate = LocalDate.now();
        this.forecastDate = data.forecastDate();
        this.status = rentalStatus;
    }
}
