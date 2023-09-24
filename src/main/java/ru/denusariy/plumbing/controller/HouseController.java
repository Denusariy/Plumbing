package ru.denusariy.plumbing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denusariy.plumbing.domain.dto.request.AssignPlumberDto;
import ru.denusariy.plumbing.domain.dto.request.HouseRequestDTO;
import ru.denusariy.plumbing.domain.dto.request.HouseToRepairDto;
import ru.denusariy.plumbing.domain.dto.response.AssignedPlumberDto;
import ru.denusariy.plumbing.domain.dto.response.HouseResponseDTO;
import ru.denusariy.plumbing.domain.dto.response.RepairedHouseDto;
import ru.denusariy.plumbing.service.HouseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/houses")
public class HouseController {
    private final HouseService houseService;
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Получение дома по id. Возвращает JSON с информацией о доме", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    public ResponseEntity<HouseResponseDTO> show(@PathVariable("id") Long id) {
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
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(houseService.delete(id));
    }

    @PostMapping(value = "/assign-plumber", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Назначение сантехника с указанным в теле запроса именем на обслуживание дома с указанным " +
            "в пути id. Возвращает имя назначенного сантехника", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    public ResponseEntity<AssignedPlumberDto> assignPlumberToHouse(@RequestBody AssignPlumberDto assignPlumberDto) {
        return ResponseEntity.ok()
                .body(houseService.assignPlumberToHouse(assignPlumberDto));
    }

    @PostMapping(value = "/release/{id}", produces = {MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Открепление сантехника от дома с указанным в пути id. Возвращает имя открепленного сантехника",
            responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    public ResponseEntity<String> release(@PathVariable("id") Long houseId) {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(houseService.release(houseId));
    }

    @PostMapping(value = "/repair-house", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Удаление сантехника по id. Возвращает имя уволенного сантехника", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    public ResponseEntity<RepairedHouseDto> repairHouse(@RequestBody HouseToRepairDto house) {
        return ResponseEntity.ok()
                .body(houseService.repairHouse(house));
    }
}
