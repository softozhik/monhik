package me.yozh.monhik.exception;

import lombok.extern.slf4j.Slf4j;
import me.yozh.monhik.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Обрабатывает ошибки, когда тип параметра в запросе не соответствует ожидаемому.
     * Например, передали строку "abc" вместо времени для maxLengthHours.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String parameterName = ex.getName();
        String requiredType = ex.getRequiredType().getSimpleName();

        String message = String.format("Некорректное значение для параметра '%s'. Ожидался тип '%s'.", parameterName, requiredType);
        log.warn(message + " Получено значение: '" + ex.getValue() + "'");

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Некорректный запрос");
        errorResponse.addValidationError(parameterName, "Значение должно соответствовать типу " + requiredType);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает все остальные непредвиденные исключения.
     * Это "защитная сетка", чтобы пользователь никогда не увидел полный стектрейс.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception ex) {
        log.error("Произошла непредвиденная ошибка", ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Произошла внутренняя ошибка сервера. Пожалуйста, попробуйте позже."
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}