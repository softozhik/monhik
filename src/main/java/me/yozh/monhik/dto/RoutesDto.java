package me.yozh.monhik.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import me.yozh.monhik.entity.Routes;

import java.time.LocalTime;

@Data
@Builder
@Schema(description = "Информация о туристическом маршруте")
public class RoutesDto {

    @Schema(description = "Название маршрута", example = "Большой Тхач", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Длина маршрута в километрах", example = "15.5")
    private Double lengthKm;

    @Schema(description = "Ориентировочная продолжительность маршрута в часах", example = "4:30")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime lengthHours;

    @Schema(description = "Уровень сложности маршрута", example = "MEDIUM")
    private String difficulty;

    @Schema(description = "Географический район маршрута", example = "NORTH")
    private String region;

    public static RoutesDto toDto(Routes routes) {
        return RoutesDto.builder()
                .name(routes.getName())
                .lengthKm(routes.getLengthKm())
                .lengthHours(routes.getLengthHours())
                .difficulty(routes.getDifficulty().toString())
                .region(routes.getRegion().toString())
                .build();
    }
}
