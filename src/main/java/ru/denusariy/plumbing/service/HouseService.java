package ru.denusariy.plumbing.service;

import ru.denusariy.plumbing.domain.dto.request.HouseRequestDTO;
import ru.denusariy.plumbing.domain.dto.request.PlumberRequestDTO;
import ru.denusariy.plumbing.domain.dto.response.HouseResponseDTO;

public interface HouseService {
    HouseResponseDTO findOne(int id);

    HouseResponseDTO save(HouseRequestDTO newHouse);

    String delete(int id);

    String assign(int houseId, PlumberRequestDTO plumber);

    String release(int houseId);
}
