package com.library.api.services;

import com.library.api.dtos.customer.CustomerRequestDTO;
import com.library.api.entities.Customer;

public interface CustomerService {
    Customer createCustomer(CustomerRequestDTO data);
}
