package com.library.api.specifications;

import com.library.api.entities.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.text.Normalizer;

public class CustomerSpecification {
    public static Specification<Customer> containsTextInColumns(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String normalizedSearch = removeAccents(search.toLowerCase());
            String likeSearch = "%" + normalizedSearch + "%";

            Predicate idPredicate = criteriaBuilder.like(criteriaBuilder.concat(root.get("id").as(String.class), ""), likeSearch);
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.function("unaccent", String.class, root.get("name"))), likeSearch);
            Predicate emailPredicate = criteriaBuilder.like(root.get("email"), likeSearch);
            Predicate addressPredicate = criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.function("unaccent", String.class, root.get("address"))), likeSearch);
            Predicate cityPredicate = criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.function("unaccent", String.class, root.get("city"))), likeSearch);
            return criteriaBuilder.or(idPredicate, namePredicate, emailPredicate, addressPredicate, cityPredicate);
        };
    }

    public static String removeAccents(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
