package com.library.api.repositories;

import com.library.api.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    boolean existsByName(String name);
    boolean existsByPublisherId(Long publisherId);
}
