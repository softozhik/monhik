package me.yozh.monhik.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "routes")
@Schema(description = "Информация о туристическом маршруте")
public class Routes {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
//    @Schema(description = "Уникальный идентификатор маршрута", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    /**
     * Название маршрута
     */
    @Column(name = "name", nullable = false)
//    @Schema(description = "Название маршрута", example = "Большой Тхач", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    /**
     * Длина в километрах
     */
    @Column(name = "length_km")
//    @Schema(description = "Длина маршрута в километрах", example = "15.5")
    private Double lengthKm;

    /**
     * Длина в часах
     */
    @Column(name = "length_hours")
//    @Schema(description = "Ориентировочная продолжительность маршрута в часах", example = "4:30")
//    @JsonFormat(pattern = "HH:mm")
    private LocalTime lengthHours;

    /**
     * Сложность
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
//    @Schema(description = "Уровень сложности маршрута", example = "MEDIUM")
    private Difficulty difficulty;

    /**
     * Район
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "region")
//    @Schema(description = "Географический район маршрута", example = "NORTH")
    private Region region;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Routes route = (Routes) o;
        // Для новых (transient) объектов id может быть null,
        // поэтому сравниваем по наличию id и его значению.
        return id != null && Objects.equals(id, route.id);
    }

    @Override
    public int hashCode() {
        // Используем константу для еще не сохраненных объектов
        return id != null ? id.hashCode() : getClass().hashCode();
    }

    @Schema(description = "Уровень сложности маршрута")
    public enum Difficulty {
        @Schema(description = "Легкий уровень сложности")
        EASY,

        @Schema(description = "Средний уровень сложности")
        MEDIUM,

        @Schema(description = "Сложный уровень")
        HARD,

        @Schema(description = "Экстремальный уровень сложности")
        EXTREME
    }

    @Schema(description = "Географический район маршрута")
    public enum Region {
        @Schema(description = "Северный район")
        NORTH,

        @Schema(description = "Южный район")
        SOUTH,

        @Schema(description = "Восточный район")
        EAST,

        @Schema(description = "Западный район")
        WEST,

        @Schema(description = "Центральный район")
        CENTRAL
    }
}
