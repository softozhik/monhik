package me.yozh.monhik.repository.specification;

import jakarta.persistence.criteria.Predicate;
import me.yozh.monhik.entity.Routes;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalTime; // <-- Добавляем импорт
import java.util.ArrayList;
import java.util.List;

@Component
public class RoutesSpecification {

    public Specification<Routes> byCriteria(
            Routes.Region region,
            Routes.Difficulty difficulty,
            Double minLengthKm,
            Double maxLengthKm,
            LocalTime maxLengthHours // <-- 1. Добавляем новый параметр
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Если параметр не null, добавляем его в список условий
            if (region != null) {
                predicates.add(criteriaBuilder.equal(root.get("region"), region));
            }

            if (difficulty != null) {
                predicates.add(criteriaBuilder.equal(root.get("difficulty"), difficulty));
            }

            if (minLengthKm != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("lengthKm"), minLengthKm));
            }

            if (maxLengthKm != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("lengthKm"), maxLengthKm));
            }

            // 2. Добавляем новое условие для фильтрации по времени
            if (maxLengthHours != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("lengthHours"), maxLengthHours));
            }

            // Объединяем все условия через AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}