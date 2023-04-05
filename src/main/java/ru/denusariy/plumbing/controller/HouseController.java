package ru.denusariy.plumbing.controller;

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

    @GetMapping("/{id}")
    public ResponseEntity<HouseResponseDTO> show(@PathVariable("id") int id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(houseService.findOne(id));
    }

    @PostMapping()
    public ResponseEntity<HouseResponseDTO> create(@RequestBody HouseRequestDTO newHouse) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(houseService.save(newHouse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(houseService.delete(id));
    }

    @PostMapping("/assign/{id}")
    public ResponseEntity<String> assign(@PathVariable("id") int houseId,
                                         @ModelAttribute("plumber") String plumberName) {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(houseService.assign(houseId, plumberName));
    }

    @PostMapping("/release/{id}")
    public ResponseEntity<String> release(@PathVariable("id") int houseId) {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(houseService.release(houseId));
    }
}
