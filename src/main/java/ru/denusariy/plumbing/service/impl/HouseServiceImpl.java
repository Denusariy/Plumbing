package ru.denusariy.plumbing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.denusariy.plumbing.domain.dto.request.HouseRequestDTO;
import ru.denusariy.plumbing.domain.dto.request.PlumberRequestDTO;
import ru.denusariy.plumbing.domain.dto.response.HouseResponseDTO;
import ru.denusariy.plumbing.domain.entity.House;
import ru.denusariy.plumbing.domain.entity.Plumber;
import ru.denusariy.plumbing.exception.HouseNotFoundException;
import ru.denusariy.plumbing.exception.OutOfHousesLimitException;
import ru.denusariy.plumbing.exception.PlumberNotFoundException;
import ru.denusariy.plumbing.repository.HouseRepository;
import ru.denusariy.plumbing.repository.PlumberRepository;
import ru.denusariy.plumbing.service.HouseService;

@Service
@Log4j2
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {
    private final HouseRepository houseRepository;
    private final PlumberRepository plumberRepository;
    private final ModelMapper modelMapper;


    @Override
    @Transactional(readOnly = true)
    public HouseResponseDTO findOne(int id) {
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
    public String delete(int id) {
        House house = houseRepository.findById(id).orElseThrow(() -> new HouseNotFoundException(
                String.format("Дом с id %d не найден", id)));
        String address = house.getAddress();
        houseRepository.delete(house);
        log.info("Удален дом по адресу " + address);
        return address;
    }

    @Override
    @Transactional
    public String assign(int houseId, PlumberRequestDTO plumber) {
        String name = plumber.getName();
        Plumber appointed = plumberRepository.findByNameEquals(name).orElseThrow(() ->
                new PlumberNotFoundException(String.format("Сантехник с именем %s не найден", name)));
        House house = houseRepository.findById(houseId).orElseThrow(() -> new HouseNotFoundException(
                String.format("Дом с id %d не найден", houseId)));
        house.setPlumber(appointed);
        if(appointed.getHouses().size() > 5)
            throw new OutOfHousesLimitException("Сантехник может обслуживать не более 5 домов. Следует назначить " +
                    "другого специалиста.");
        log.info(String.format("По адресу %s назначен сантехник %s", house.getAddress(), name));
        return name;
    }

    @Override
    @Transactional
    public String release(int houseId) {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new HouseNotFoundException(
                String.format("Дом с id %d не найден", houseId)));
        String name = house.getPlumber().getName();
        house.setPlumber(null);
        log.info(String.format("Сантехник %s больше не обслуживает дом по адресу %s", name, house.getAddress()));
        return name;
    }

    public House convertToEntity(HouseRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, House.class);
    }

    public HouseResponseDTO convertToDTO(House house) {
        return modelMapper.map(house, HouseResponseDTO.class);
    }
}
