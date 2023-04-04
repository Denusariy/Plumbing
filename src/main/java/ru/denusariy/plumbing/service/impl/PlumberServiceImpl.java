package ru.denusariy.plumbing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.denusariy.plumbing.domain.dto.request.PlumberRequestDTO;
import ru.denusariy.plumbing.domain.dto.response.PlumberResponseDTO;
import ru.denusariy.plumbing.domain.entity.Plumber;
import ru.denusariy.plumbing.exception.PlumberNotFoundException;
import ru.denusariy.plumbing.repository.PlumberRepository;
import ru.denusariy.plumbing.service.PlumberService;
@Service
@Log4j2
@RequiredArgsConstructor
public class PlumberServiceImpl implements PlumberService {
    private final PlumberRepository plumberRepository;
    private final ModelMapper modelMapper;
    @Override
    @Transactional(readOnly = true)
    public PlumberResponseDTO findOne(int id) {
        return convertToDTO(plumberRepository.findById(id).orElseThrow(() -> new PlumberNotFoundException(
                String.format("Сантехник с id %d не найден", id))));
    }

    @Override
    @Transactional
    public PlumberResponseDTO save(PlumberRequestDTO newPlumber) {
        log.info("Сохранен новый сантехник по имени " + newPlumber.getName());
        return convertToDTO(plumberRepository.save(convertToEntity(newPlumber)));
    }

    @Override
    @Transactional
    public String delete(int id) {
        Plumber plumber = plumberRepository.findById(id).orElseThrow(() -> new PlumberNotFoundException(
                String.format("Сантехник с id %d не найден", id)));
        String name = plumber.getName();
        plumberRepository.delete(plumber);
        log.info("Удален сантехник по имени " + name); //TODO проверить, надо ли освобождать дома
        return name;
    }

    public Plumber convertToEntity(PlumberRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, Plumber.class);
    }

    public PlumberResponseDTO convertToDTO(Plumber plumber) {
        return modelMapper.map(plumber,PlumberResponseDTO.class);
    }
}
