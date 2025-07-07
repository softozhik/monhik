package me.yozh.monhik.service;

import lombok.RequiredArgsConstructor;
import me.yozh.monhik.dto.RoutesDto;
import me.yozh.monhik.entity.Routes;
import me.yozh.monhik.repository.RoutesRepository;
import me.yozh.monhik.repository.specification.RoutesSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalTime; // <-- Добавляем импорт
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutesService {

    private final RoutesRepository routesRepository;
    private final RoutesSpecification routesSpecification;

    /**
     * Находит все маршруты без фильтрации.
     */
    public List<RoutesDto> findAll() {
        return routesRepository.findAll().stream().map(RoutesDto::toDto).toList();
    }

    /**
     * Находит маршруты по заданным критериям.
     * @param region Регион для фильтрации (опционально)
     * @param difficulty Сложность для фильтрации (опционально)
     * @param minLengthKm Минимальная протяженность в км (опционально)
     * @param maxLengthKm Максимальная протяженность в км (опционально)
     * @param maxLengthHours Максимальная продолжительность маршрута (опционально)
     * @return Список отфильтрованных маршрутов в виде DTO.
     */
    public List<RoutesDto> findByCriteria(
            Routes.Region region,
            Routes.Difficulty difficulty,
            Double minLengthKm,
            Double maxLengthKm,
            LocalTime maxLengthHours // <-- Добавляем новый параметр
    ) {
        Specification<Routes> spec = routesSpecification.byCriteria(region, difficulty, minLengthKm, maxLengthKm, maxLengthHours); // <-- Передаем его в спецификацию
        return routesRepository.findAll(spec).stream()
                .map(RoutesDto::toDto)
                .toList();
    }
}