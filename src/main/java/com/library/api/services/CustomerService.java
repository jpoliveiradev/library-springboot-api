package com.library.api.services;

import com.library.api.dtos.SummaryDataDTO;
import com.library.api.dtos.customer.CustomerRequestDTO;
import com.library.api.dtos.customer.CustomerResponseDTO;
import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.entities.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(CustomerRequestDTO data);

    PagedResultDTO<CustomerResponseDTO> getAll(int page, int size);

    CustomerResponseDTO getById(Long id);

    void updateCustomer(Long id, CustomerRequestDTO body);

    void deleteCustomer(Long id);

    List<SummaryDataDTO> getSummaryData();
}
