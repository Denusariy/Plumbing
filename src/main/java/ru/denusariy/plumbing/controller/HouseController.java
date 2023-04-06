package ru.denusariy.plumbing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.denusariy.plumbing.domain.dto.request.HouseRequestDTO;
import ru.denusariy.plumbing.domain.dto.response.HouseResponseDTO;
import ru.denusariy.plumbing.service.HouseService;

@RestController
@RequestMapping("/api/v1/house")
@RequiredArgsConstructor
public class HouseController {
    private final HouseService houseService;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Получение дома по id. Возвращает JSON с информацией о доме", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    public ResponseEntity<HouseResponseDTO> show(@PathVariable("id") int id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(houseService.findOne(id));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Добавление нового дома. Возвращает JSON с информацией о доме",
            responses = @ApiResponse(responseCode = "200", description = "OK"))
    public ResponseEntity<HouseResponseDTO> create(@RequestBody HouseRequestDTO newHouse) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(houseService.save(newHouse));
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Удаление дома по id. Возвращает адрес удаленного дома", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(houseService.delete(id));
    }

    @PostMapping(value = "/assign/{id}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Назначение сантехника с указанным в теле запроса именем на обслуживание дома с указанным " +
            "в пути id. Возвращает имя назначенного сантехника", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    public ResponseEntity<String> assign(@PathVariable("id") int houseId,
                                         @ModelAttribute("plumber") String plumberName) {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(houseService.assign(houseId, plumberName));
    }

    @PostMapping(value = "/release/{id}", produces = {MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Открепление сантехника от дома с указанным в пути id. Возвращает имя открепленного сантехника",
            responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    public ResponseEntity<String> release(@PathVariable("id") int houseId) {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(houseService.release(houseId));
    }
}
