package ru.denusariy.plumbing.service;

import ru.denusariy.plumbing.domain.dto.request.PlumberRequestDTO;
import ru.denusariy.plumbing.domain.dto.response.PlumberResponseDTO;

public interface PlumberService {
    PlumberResponseDTO findOne(int id);
    PlumberResponseDTO save(PlumberRequestDTO newPlumber);
    String delete(int id);
}
