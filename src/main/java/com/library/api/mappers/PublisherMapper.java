package com.library.api.mappers;

import com.library.api.dtos.publisher.PublisherResponseDTO;
import com.library.api.entities.Publisher;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {
    public PublisherResponseDTO mapPublisherToDTO(Publisher publisher) {
        return new PublisherResponseDTO(
                publisher.getId(),
                publisher.getName(),
                publisher.getCity()
        );
    }
}