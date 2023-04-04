package ru.denusariy.plumbing.controller;

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

    @GetMapping("/{id}")
    public ResponseEntity<PlumberResponseDTO> show(@PathVariable("id") int id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(plumberService.findOne(id));
    }

    @PostMapping("/hire")
    public ResponseEntity<PlumberResponseDTO> create(@RequestBody PlumberRequestDTO newPlumber) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(plumberService.save(newPlumber));
    }

    @DeleteMapping("/fire/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(plumberService.delete(id));
    }

}
