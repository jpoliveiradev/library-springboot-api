package com.library.api.services.impl;

import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.dtos.rentals.RentalRequestCreateDTO;
import com.library.api.dtos.rentals.RentalResponseDTO;
import com.library.api.entities.Book;
import com.library.api.entities.Customer;
import com.library.api.entities.Rental;
import com.library.api.entities.RentalStatus;
import com.library.api.entities.enums.RentalStatusEnum;
import com.library.api.mappers.RentalMapper;
import com.library.api.repositories.BookRepository;
import com.library.api.repositories.CustomerRepository;
import com.library.api.repositories.RentalRepository;
import com.library.api.repositories.RentalStatusRepository;
import com.library.api.services.RentalService;
import com.library.api.specifications.RentalSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RentalStatusRepository rentalStatusRepository;
    @Autowired
    RentalMapper rentalMapper;

    @Override
    public Rental createRental(RentalRequestCreateDTO data) {
        Book book = this.bookRepository.findById(data.book_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrada"));
        Customer customer = this.customerRepository.findById(data.customer_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrada"));
        RentalStatus rentalStatus = this.rentalStatusRepository.findById(RentalStatusEnum.PENDING.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status de aluguel não encontrado"));

        if (book.getQuantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Livro não disponível");
        }

        book.setQuantity(book.getQuantity() - 1);
        Rental newRental = new Rental(data, book, customer, rentalStatus);

        this.rentalRepository.save(newRental);
        return newRental;
    }

    @Override
    public PagedResultDTO<RentalResponseDTO> getAll(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Specification<Rental> rentalSpecification = RentalSpecification.searchSpecification(search);
        Page<Rental> rentalsPage = this.rentalRepository.findAll(rentalSpecification, pageable);
        Page<RentalResponseDTO> rentalsDTOPage = rentalsPage.map(this.rentalMapper::mapRentalToDTO);

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

        return this.rentalMapper.mapRentalToDTO(rental);
    }

    @Override
    public void returnRental(Long id) {
        Rental rental = this.rentalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluguel não encontrado"));

        rental.setReturnDate(LocalDate.now());
        rental.getBook().setQuantity(rental.getBook().getQuantity() + 1);
        RentalStatus rentalStatus;
        if (rental.getReturnDate().isAfter(rental.getForecastDate())) {
            rentalStatus = this.rentalStatusRepository.findById(RentalStatusEnum.LATE_TIME.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status de aluguel não encontrado"));
        } else {
            rentalStatus = this.rentalStatusRepository.findById(RentalStatusEnum.ON_TIME.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status de aluguel não encontrado"));
        }

        rental.setStatus(rentalStatus);
        this.rentalRepository.save(rental);
    }

    @Override
    public void deleteRental(Long id) {
        Rental rental = this.rentalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluguel não encontrado"));

        if (rental.getReturnDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível excluir o aluguel pois o livro ainda não foi devolvido");
        }

        this.rentalRepository.deleteById(id);
    }
}
