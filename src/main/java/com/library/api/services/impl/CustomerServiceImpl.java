package com.library.api.services.impl;

import com.library.api.dtos.customer.CustomerRequestDTO;
import com.library.api.entities.Customer;
import com.library.api.repositories.CustomerRepository;
import com.library.api.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
