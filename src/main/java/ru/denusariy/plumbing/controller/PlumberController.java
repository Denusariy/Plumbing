package ru.denusariy.plumbing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.denusariy.plumbing.domain.dto.request.PlumberRequestDTO;
import ru.denusariy.plumbing.domain.dto.response.PlumberResponseDTO;
import ru.denusariy.plumbing.service.PlumberService;

@RestController
@RequestMapping("/api/v1/plumber")
@RequiredArgsConstructor
public class PlumberController {
    private final PlumberService plumberService;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Получение сантехника по id. Возвращает JSON с информацией о сантехнике", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    public ResponseEntity<PlumberResponseDTO> show(@PathVariable("id") int id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(plumberService.findOne(id));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Добавление нового сантехника. Возвращает JSON с информацией о сантехнике",
            responses = @ApiResponse(responseCode = "200", description = "OK"))
    public ResponseEntity<PlumberResponseDTO> create(@RequestBody PlumberRequestDTO newPlumber) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(plumberService.save(newPlumber));
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Удаление сантехника по id. Возвращает имя уволенного сантехника", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(plumberService.delete(id));
    }

}
