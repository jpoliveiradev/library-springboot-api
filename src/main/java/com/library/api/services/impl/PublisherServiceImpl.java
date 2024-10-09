package com.library.api.services.impl;

import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.dtos.publisher.PublisherRequestDTO;
import com.library.api.dtos.publisher.PublisherResponseDTO;
import com.library.api.entities.Publisher;
import com.library.api.mappers.PublisherMapper;
import com.library.api.repositories.PublisherRepository;
import com.library.api.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PublisherServiceImpl implements PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private PublisherMapper publisherMapper;

    @Override
    public Publisher createPublisher(PublisherRequestDTO data) {
        Publisher newPublisher = new Publisher(data);
        publisherRepository.save(newPublisher);

        return newPublisher;
    }

    @Override
    public PagedResultDTO<PublisherResponseDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Publisher> publishersPage = this.publisherRepository.findAll(pageable);
        Page<PublisherResponseDTO> publishersDTOPage = publishersPage.map(publisherMapper::mapPublisherToDTO);

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

        return publisherMapper.mapPublisherToDTO(publisher);
    }
}
