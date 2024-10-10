package com.library.api.services;

import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.dtos.publisher.PublisherRequestDTO;
import com.library.api.dtos.publisher.PublisherResponseDTO;
import com.library.api.entities.Publisher;

public interface PublisherService {
    Publisher createPublisher(PublisherRequestDTO body);

    PagedResultDTO<PublisherResponseDTO> getAll(int page, int size);

    PublisherResponseDTO getById(Long id);

    void updatePublisher(Long id, PublisherRequestDTO body);

    void deletePublisher(Long id);
}
