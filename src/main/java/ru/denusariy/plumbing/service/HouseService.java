package ru.denusariy.plumbing.service;

import ru.denusariy.plumbing.domain.dto.request.AssignPlumberDto;
import ru.denusariy.plumbing.domain.dto.request.HouseRequestDTO;
import ru.denusariy.plumbing.domain.dto.request.HouseToRepairDto;
import ru.denusariy.plumbing.domain.dto.response.AssignedPlumberDto;
import ru.denusariy.plumbing.domain.dto.response.HouseResponseDTO;
import ru.denusariy.plumbing.domain.dto.response.RepairedHouseDto;

public interface HouseService {
    HouseResponseDTO findOne(Long id);

    HouseResponseDTO save(HouseRequestDTO newHouse);

    String delete(Long id);

    AssignedPlumberDto assignPlumberToHouse(AssignPlumberDto assignPlumberDto);

    String release(Long houseId);

    RepairedHouseDto repairHouse(HouseToRepairDto repairDto);
}
