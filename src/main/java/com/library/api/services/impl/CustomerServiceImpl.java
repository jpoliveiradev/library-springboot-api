package com.library.api.services.impl;

import com.library.api.dtos.SummaryDataDTO;
import com.library.api.dtos.customer.CustomerRequestDTO;
import com.library.api.dtos.customer.CustomerResponseDTO;
import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.entities.Customer;
import com.library.api.mappers.CustomerMapper;
import com.library.api.repositories.CustomerRepository;
import com.library.api.repositories.RentalRepository;
import com.library.api.services.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Customer createCustomer(CustomerRequestDTO data) {
        if (this.customerRepository.existsByEmail(data.email()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Esse email já cadastrado");

        Customer newCustomer = new Customer(data);
        this.customerRepository.save(newCustomer);
        return newCustomer;
    }

    @Override
    public PagedResultDTO<CustomerResponseDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Customer> customersPage = this.customerRepository.findAll(pageable);
        Page<CustomerResponseDTO> customersDTOPage = customersPage.map(this.customerMapper::mapCustomerToDTO);

        return new PagedResultDTO<>(
                customersDTOPage.getContent(),
                customersDTOPage.getNumber(),
                customersDTOPage.getSize(),
                customersDTOPage.getTotalElements(),
                customersDTOPage.getTotalPages()
        );
    }

    @Override
    public CustomerResponseDTO getById(Long id) {
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        return this.customerMapper.mapCustomerToDTO(customer);
    }

    @Override
    public List<SummaryDataDTO> getSummaryData() {
       List<Customer> customers = this.customerRepository.findAll();

       return customers.stream().map(this.customerMapper::mapCustomerToSummaryDataDTO)
               .collect(Collectors.toList());
    }

    @Override
    public void updateCustomer(Long id, CustomerRequestDTO data) {
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        customer.setName(data.name());
        customer.setEmail(data.email());
        customer.setAddress(data.address());
        customer.setCity(data.city());

        this.customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!this.customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        if (this.rentalRepository.existsByCustomerId(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Existem aluguéis cadastrados com esse cliente");
        }
        this.customerRepository.deleteById(id);
    }
}
