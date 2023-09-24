package ru.denusariy.plumbing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.denusariy.plumbing.domain.dto.request.AssignPlumberDto;
import ru.denusariy.plumbing.domain.dto.request.HouseRequestDTO;
import ru.denusariy.plumbing.domain.dto.request.HouseToRepairDto;
import ru.denusariy.plumbing.domain.dto.response.AssignedPlumberDto;
import ru.denusariy.plumbing.domain.dto.response.HouseResponseDTO;
import ru.denusariy.plumbing.domain.dto.response.RepairedHouseDto;
import ru.denusariy.plumbing.domain.entity.House;
import ru.denusariy.plumbing.domain.entity.Plumber;
import ru.denusariy.plumbing.exception.HouseNotFoundException;
import ru.denusariy.plumbing.exception.OutOfHousesLimitException;
import ru.denusariy.plumbing.exception.PlumberNotFoundException;
import ru.denusariy.plumbing.repository.HouseRepository;
import ru.denusariy.plumbing.repository.PlumberRepository;
import ru.denusariy.plumbing.service.HouseService;

@Slf4j
@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {
    private final ModelMapper modelMapper;
    private final HouseRepository houseRepository;
    private final PlumberRepository plumberRepository;

    @Override
    @Transactional(readOnly = true)
    public HouseResponseDTO findOne(Long id) {
        return convertToDTO(houseRepository.findById(id).orElseThrow(() -> new HouseNotFoundException(
                String.format("Дом с id %d не найден", id))));
    }

    @Override
    @Transactional
    public HouseResponseDTO save(HouseRequestDTO newHouse) {
        log.info("Добавлен новый дом по адресу " + newHouse.getAddress());
        return convertToDTO(houseRepository.save(convertToEntity(newHouse)));
    }

    @Override
    @Transactional
    public String delete(Long id) {
        House house = houseRepository.findById(id).orElseThrow(() -> new HouseNotFoundException(
                String.format("Дом с id %d не найден", id)));
        String address = house.getAddress();
        houseRepository.delete(house);
        log.info("Удален дом по адресу " + address);
        return address;
    }

    @Override
    @Transactional
    public AssignedPlumberDto assignPlumberToHouse(AssignPlumberDto assignPlumberDto) {
        Plumber assigned = plumberRepository.findById(assignPlumberDto.getPlumberId()).orElseThrow(() -> new PlumberNotFoundException(
                String.format("Сантехник с id %d не найден", assignPlumberDto.getPlumberId())));
        if (assigned.getHouses().size() >= 5) {
            throw new OutOfHousesLimitException("Сантехник может обслуживать не более 5 домов. Следует назначить " +
                    "другого специалиста.");
        }
        House house = houseRepository.findById(assignPlumberDto.getHouseId()).orElseThrow(() -> new HouseNotFoundException(
                String.format("Дом с id %d не найден", assignPlumberDto.getHouseId())));
        house.setPlumber(assigned);
        log.info(String.format("По адресу %s назначен сантехник %s", house.getAddress(), assigned.getName()));
        return AssignedPlumberDto.builder()
                .result(String.format("По адресу %s назначен сантехник %s", house.getAddress(), assigned.getName()))
                .build();
    }

    @Override
    @Transactional
    public String release(Long houseId) {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new HouseNotFoundException(
                String.format("Дом с id %d не найден", houseId)));
        String name = house.getPlumber().getName();
        house.setPlumber(null);
        log.info(String.format("Сантехник %s больше не обслуживает дом по адресу %s", name, house.getAddress()));
        return name;
    }

    @Override
    @Transactional
    public RepairedHouseDto repairHouse(HouseToRepairDto repairDto) {
        Plumber plumber = plumberRepository.findById(repairDto.getPlumberId()).orElseThrow(() -> new PlumberNotFoundException(
                String.format("Сантехник с id %d не найден", repairDto.getPlumberId())));
        House house = houseRepository.findById(repairDto.getHouseId()).orElseThrow(() -> new HouseNotFoundException(
                String.format("Дом с id %d не найден", repairDto.getHouseId())
        ));
        log.info("Doing repair...");
        long houseBudgetAfterRepair = house.getBudget() - repairDto.getRepairPrice();
        if (houseBudgetAfterRepair < 0) {
            throw new RuntimeException("House has not got enough money for repair");
        }
        house.setBudget(houseBudgetAfterRepair);
        long plumberBankAccAfterRepair = plumber.getBankAccount() + repairDto.getRepairPrice();
        plumber.setBankAccount(plumberBankAccAfterRepair);
        return RepairedHouseDto.builder()
                .plumberName(plumber.getName())
                .houseAddress(house.getAddress())
                .repairPrice(repairDto.getRepairPrice())
                .houseBudget(house.getBudget())
                .build();
    }

    public House convertToEntity(HouseRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, House.class);
    }

    public HouseResponseDTO convertToDTO(House house) {
        return modelMapper.map(house, HouseResponseDTO.class);
    }
}
