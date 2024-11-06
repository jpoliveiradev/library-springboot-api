package com.library.api.specifications;

import com.library.api.entities.Book;
import com.library.api.entities.Customer;
import com.library.api.entities.Rental;
import com.library.api.utils.AccentRemover;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class RentalSpecification {
    public static Specification<Rental> searchSpecification(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String normalizedSearch = AccentRemover.removeAccents(search.toLowerCase());
            String likeSearch = "%" + normalizedSearch + "%";

            Join<Book, Book> bookJoin = root.join("book", JoinType.LEFT);
            Join<Book, Customer> customerJoin = root.join("customer", JoinType.LEFT);

            Predicate idPredicate = criteriaBuilder.like(criteriaBuilder.concat(root.get("id").as(String.class), ""), likeSearch);
            Predicate customerNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.function("unaccent", String.class, customerJoin.get("name"))), likeSearch);
            Predicate bookNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.function("unaccent", String.class, bookJoin.get("name"))), likeSearch);

            Predicate rentalDatePredicate = criteriaBuilder.like(criteriaBuilder.function("to_char", String.class, root.get("rentalDate"), criteriaBuilder.literal("YYYY-MM-DD")), likeSearch);
            Predicate forecastDatePredicate = criteriaBuilder.like(criteriaBuilder.function("to_char", String.class, root.get("forecastDate"), criteriaBuilder.literal("YYYY-MM-DD")), likeSearch);
            Predicate returnDatePredicate = criteriaBuilder.like(criteriaBuilder.function("to_char", String.class, root.get("returnDate"), criteriaBuilder.literal("YYYY-MM-DD")), likeSearch);
            return criteriaBuilder.or(idPredicate, customerNamePredicate, bookNamePredicate, rentalDatePredicate, forecastDatePredicate, returnDatePredicate);
        };
    }
}
