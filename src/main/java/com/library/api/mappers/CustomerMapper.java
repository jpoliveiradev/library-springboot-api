package com.library.api.mappers;

import com.library.api.dtos.customer.CustomerRequestDTO;
import com.library.api.dtos.customer.CustomerResponseDTO;
import com.library.api.entities.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerResponseDTO mapCustomerToDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getCity()
        );
    }
}
