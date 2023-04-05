package ru.denusariy.plumbing.domain.dto.response.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponseDTO {
    private final HttpStatus status;
    private final String message;
}
