package com.library.api.services.impl;


import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.dtos.rentals.RentalRequestCreateDTO;
import com.library.api.dtos.rentals.RentalResponseDTO;
import com.library.api.entities.Book;
import com.library.api.entities.Customer;
import com.library.api.entities.Rental;
import com.library.api.mappers.RentalMapper;
import com.library.api.repositories.BookRepository;
import com.library.api.repositories.CustomerRepository;
import com.library.api.repositories.RentalRepository;
import com.library.api.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    RentalMapper rentalMapper;

    @Override
    public Rental createRental(RentalRequestCreateDTO data) {
        Book book = bookRepository.findById(data.book_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrada"));
        Customer customer = customerRepository.findById(data.customer_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrada"));

        Rental newRental = new Rental(data, book, customer);
        rentalRepository.save(newRental);

        return newRental;
    }

    @Override
    public PagedResultDTO<RentalResponseDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Rental> rentalsPage = this.rentalRepository.findAll(pageable);
        Page<RentalResponseDTO> rentalsDTOPage = rentalsPage.map(rentalMapper::mapRentalToDTO);

        return new PagedResultDTO<>(
                rentalsDTOPage.getContent(),
                rentalsDTOPage.getNumber(),
                rentalsDTOPage.getSize(),
                rentalsDTOPage.getTotalElements(),
                rentalsDTOPage.getTotalPages()
        );
    }

    @Override
    public RentalResponseDTO getById(Long id) {
        Rental rental = this.rentalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluguel não encontrado"));

        return rentalMapper.mapRentalToDTO(rental);
    }
}
