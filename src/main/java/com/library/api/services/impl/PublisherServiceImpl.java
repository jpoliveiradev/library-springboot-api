package com.library.api.services.impl;

import com.library.api.dtos.SummaryDataDTO;
import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.dtos.publisher.PublisherRequestDTO;
import com.library.api.dtos.publisher.PublisherResponseDTO;
import com.library.api.entities.Publisher;
import com.library.api.mappers.PublisherMapper;
import com.library.api.repositories.BookRepository;
import com.library.api.repositories.PublisherRepository;
import com.library.api.services.PublisherService;
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
public class PublisherServiceImpl implements PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherMapper publisherMapper;

    @Override
    public Publisher createPublisher(PublisherRequestDTO data) {
        if (this.publisherRepository.existsByName(data.name()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Editora já cadastrada");

        Publisher newPublisher = new Publisher(data);
        this.publisherRepository.save(newPublisher);

        return newPublisher;
    }

    @Override
    public PagedResultDTO<PublisherResponseDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Publisher> publishersPage = this.publisherRepository.findAll(pageable);
        Page<PublisherResponseDTO> publishersDTOPage = publishersPage.map(this.publisherMapper::mapPublisherToDTO);

        return new PagedResultDTO<>(
                publishersDTOPage.getContent(),
                publishersDTOPage.getNumber(),
                publishersDTOPage.getSize(),
                publishersDTOPage.getTotalElements(),
                publishersDTOPage.getTotalPages()
        );
    }

    @Override
    public PublisherResponseDTO getById(Long id) {
        Publisher publisher = this.publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editora não encontrada"));

        return this.publisherMapper.mapPublisherToDTO(publisher);
    }

    @Override
    public List<SummaryDataDTO> getSummaryData() {
        List<Publisher> publishers = this.publisherRepository.findAll();

        return publishers.stream().map(this.publisherMapper::mapPublisherToSummaryDataDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updatePublisher(Long id, PublisherRequestDTO data) {
        Publisher publisher = this.publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editora não encontrada"));

        if (!data.name().equals(publisher.getName()) && this.publisherRepository.existsByName(data.name()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Editora já cadastrada");

        publisher.setName(data.name());
        publisher.setCity(data.city());

        this.publisherRepository.save(publisher);
    }

    @Override
    public void deletePublisher(Long id) {
        if (!this.publisherRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Editora não encontrada");
        if (this.bookRepository.existsByPublisherId(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Existem livros cadastrados com essa editora");

        this.publisherRepository.deleteById(id);
    }
}
