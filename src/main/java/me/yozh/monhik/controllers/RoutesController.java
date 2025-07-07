package me.yozh.monhik.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.yozh.monhik.dto.RoutesDto;
import me.yozh.monhik.entity.Routes;
import me.yozh.monhik.service.RoutesService;
import org.springframework.format.annotation.DateTimeFormat; // <-- Добавляем импорт
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime; // <-- Добавляем импорт
import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
@Tag(name = "Routes", description = "API для работы с маршрутами")
public class RoutesController {

    private final RoutesService routesService;

    @Operation(
            summary = "Получить все маршруты с возможностью фильтрации",
            description = "Возвращает список всех доступных маршрутов. Если параметры не указаны, вернет все маршруты. " +
                    "Если указаны, применит фильтрацию по ним."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение списка маршрутов",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RoutesDto.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content
            )
    })
    @GetMapping
    public List<RoutesDto> getRoutes(
            @Parameter(description = "Фильтр по региону", schema = @Schema(implementation = Routes.Region.class))
            @RequestParam(required = false) Routes.Region region,

            @Parameter(description = "Фильтр по сложности", schema = @Schema(implementation = Routes.Difficulty.class))
            @RequestParam(required = false) Routes.Difficulty difficulty,

            @Parameter(description = "Минимальная протяженность маршрута в км (например, 5.0)")
            @RequestParam(required = false) Double minLengthKm,

            @Parameter(description = "Максимальная протяженность маршрута в км (например, 15.5)")
            @RequestParam(required = false) Double maxLengthKm,

            // Добавляем новый параметр для фильтрации
            @Parameter(description = "Максимальная продолжительность маршрута (формат HH:mm или HH:mm:ss)",
                    schema = @Schema(type = "string", format = "time", example = "05:30"))
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) // Помогает Spring правильно распарсить строку в LocalTime
            LocalTime maxLengthHours
    ) {
        // Передаем новый параметр в сервис
        return routesService.findByCriteria(region, difficulty, minLengthKm, maxLengthKm, maxLengthHours);
    }
}