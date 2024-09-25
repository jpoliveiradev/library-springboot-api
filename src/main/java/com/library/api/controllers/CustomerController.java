package com.library.api.controllers;

import com.library.api.dtos.customer.CustomerRequestDTO;
import com.library.api.dtos.customer.CustomerResponseDTO;
import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.entities.Customer;
import com.library.api.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody CustomerRequestDTO body) {
        Customer customer = this.customerService.createCustomer(body);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<PagedResultDTO<CustomerResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PagedResultDTO<CustomerResponseDTO> allCustomers = this.customerService.getAll(page, size);
        return ResponseEntity.ok(allCustomers);
    }
}
