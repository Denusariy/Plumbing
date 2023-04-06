package ru.denusariy.plumbing.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.denusariy.plumbing.domain.dto.response.errors.ErrorResponseDTO;
import ru.denusariy.plumbing.exception.HouseNotFoundException;
import ru.denusariy.plumbing.exception.OutOfHousesLimitException;
import ru.denusariy.plumbing.exception.PlumberNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({HouseNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handle(HouseNotFoundException e) {
        return new ErrorResponseDTO(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({PlumberNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handle(PlumberNotFoundException e) {
        return new ErrorResponseDTO(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({OutOfHousesLimitException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handle(OutOfHousesLimitException e) {
        return new ErrorResponseDTO(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
