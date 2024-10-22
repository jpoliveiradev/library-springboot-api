package com.library.api.controllers;

import com.library.api.dtos.SummaryDataDTO;
import com.library.api.dtos.pagination.PagedResultDTO;
import com.library.api.dtos.publisher.PublisherRequestDTO;
import com.library.api.dtos.publisher.PublisherResponseDTO;
import com.library.api.entities.Publisher;
import com.library.api.services.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
@Tag(name = "Publisher")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping
    @Operation(summary = "Create publisher")
    public ResponseEntity<Publisher> create(@RequestBody @Valid PublisherRequestDTO body) {
        Publisher publisher = this.publisherService.createPublisher(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(publisher);
    }

    @GetMapping
    @Operation(summary = "Get all publishers")
    public ResponseEntity<PagedResultDTO<PublisherResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PagedResultDTO<PublisherResponseDTO> allCustomers = this.publisherService.getAll(page, size);
        return ResponseEntity.ok(allCustomers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get publisher by id")
    public ResponseEntity<PublisherResponseDTO> getById(@PathVariable Long id) {
        PublisherResponseDTO publisher = this.publisherService.getById(id);
        return ResponseEntity.ok(publisher);
    }

    @GetMapping("/summary-data")
    @Operation(summary = "get summary data of publishers")
    public ResponseEntity<List<SummaryDataDTO>> getSummaryData() {
        List<SummaryDataDTO> publishers = this.publisherService.getSummaryData();
        return ResponseEntity.ok(publishers);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody PublisherRequestDTO body) {
        this.publisherService.updatePublisher(id, body);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete publisher")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.publisherService.deletePublisher(id);
        return ResponseEntity.noContent().build();
    }
}
