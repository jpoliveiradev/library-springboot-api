package com.library.api.services.impl;

import com.library.api.dtos.customer.CustomerRequestDTO;
import com.library.api.dtos.customer.CustomerResponseDTO;
import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.entities.Customer;
import com.library.api.repositories.CustomerRepository;
import com.library.api.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(CustomerRequestDTO data) {
        Customer newCustomer = new Customer(data);

        customerRepository.save(newCustomer);
        return newCustomer;
    }

    @Override
    public PagedResultDTO<CustomerResponseDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Customer> customersPage = this.customerRepository.findAll(pageable);

        Page<CustomerResponseDTO> customersDTOPage = customersPage.map(customer -> new CustomerResponseDTO(
                customer.getId(),
                customer.getCreatedAt(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getCity()
        ));

        return new PagedResultDTO<>(
                customersDTOPage.getContent(),
                customersDTOPage.getNumber(),
                customersDTOPage.getSize(),
                customersDTOPage.getTotalElements(),
                customersDTOPage.getTotalPages()
        );
    }
}
