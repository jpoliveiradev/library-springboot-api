package com.library.api.controllers;

import com.library.api.dtos.SummaryDataDTO;
import com.library.api.dtos.customer.CustomerRequestDTO;
import com.library.api.dtos.customer.CustomerResponseDTO;
import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.entities.Customer;
import com.library.api.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    @Operation(summary = "Create customer")
    public ResponseEntity<Customer> create(@RequestBody @Valid CustomerRequestDTO body) {
        Customer customer = this.customerService.createCustomer(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @GetMapping
    @Operation(summary = "Get all customers")
    public ResponseEntity<PagedResultDTO<CustomerResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PagedResultDTO<CustomerResponseDTO> allCustomers = this.customerService.getAll(page, size);
        return ResponseEntity.ok(allCustomers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by id")
    public ResponseEntity<CustomerResponseDTO> getById(@PathVariable Long id) {
        CustomerResponseDTO customer = this.customerService.getById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/summary-data")
    @Operation(summary = "get summary data of customers")
    public ResponseEntity<List<SummaryDataDTO>> getSummaryData() {
        List<SummaryDataDTO> customers = this.customerService.getSummaryData();
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody CustomerRequestDTO body) {
        this.customerService.updateCustomer(id, body);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
