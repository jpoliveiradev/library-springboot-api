package com.library.api.specifications;

import com.library.api.entities.Book;
import com.library.api.entities.Publisher;
import com.library.api.utils.AccentRemover;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> searchSpecification(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String normalizedSearch = AccentRemover.removeAccents(search.toLowerCase());
            String likeSearch = "%" + normalizedSearch + "%";

            Join<Book, Publisher> publisherJoin = root.join("publisher", JoinType.LEFT);

            Predicate idPredicate = criteriaBuilder.like(criteriaBuilder.concat(root.get("id").as(String.class), ""), likeSearch);
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.function("unaccent", String.class, root.get("name"))), likeSearch);
            Predicate authorPredicate = criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.function("unaccent", String.class, root.get("author"))), likeSearch);
            Predicate releaseYearPredicate = criteriaBuilder.like(criteriaBuilder.concat(root.get("releaseYear").as(String.class), ""), likeSearch);
            Predicate quantityPredicate = criteriaBuilder.like(criteriaBuilder.concat(root.get("quantity").as(String.class), ""), likeSearch);
            Predicate publisherNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.function("unaccent", String.class, publisherJoin.get("name"))), likeSearch);
            return criteriaBuilder.or(idPredicate, namePredicate, authorPredicate, releaseYearPredicate, quantityPredicate, publisherNamePredicate);
        };
    }
}
