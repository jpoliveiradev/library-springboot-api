package com.library.api.specifications;

import com.library.api.entities.Publisher;
import com.library.api.utils.AccentRemover;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class PublisherSpecification {
    public static Specification<Publisher> searchSpecification(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String normalizedSearch = AccentRemover.removeAccents(search.toLowerCase());
            String likeSearch = "%" + normalizedSearch + "%";

            Predicate idPredicate = criteriaBuilder.like(criteriaBuilder.concat(root.get("id").as(String.class), ""), likeSearch);
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.function("unaccent", String.class, root.get("name"))), likeSearch);
            Predicate cityPredicate = criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.function("unaccent", String.class, root.get("city"))), likeSearch);
            return criteriaBuilder.or(idPredicate, namePredicate, cityPredicate);
        };
    }
}
