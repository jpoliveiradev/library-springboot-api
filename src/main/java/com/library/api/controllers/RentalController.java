package com.library.api.controllers;

import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.dtos.rentals.RentalRequestCreateDTO;
import com.library.api.dtos.rentals.RentalResponseDTO;
import com.library.api.entities.Rental;
import com.library.api.services.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Rental")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping
    @Operation(summary = "Create rental")
    public ResponseEntity<Rental> create(@RequestBody @Valid RentalRequestCreateDTO body) {
        Rental rental = this.rentalService.createRental(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(rental);
    }

    @GetMapping
    @Operation(summary = "Get all rentals")
    public ResponseEntity<PagedResultDTO<RentalResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PagedResultDTO<RentalResponseDTO> allRentals = this.rentalService.getAll(page, size);
        return ResponseEntity.ok(allRentals);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get rental by id")
    public ResponseEntity<RentalResponseDTO> getById(@PathVariable Long id) {
        RentalResponseDTO rental = this.rentalService.getById(id);
        return ResponseEntity.ok(rental);
    }
}
