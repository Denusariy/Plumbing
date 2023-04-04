package ru.denusariy.plumbing.domain.dto.response;

import lombok.Getter;
import lombok.Setter;
import ru.denusariy.plumbing.domain.entity.House;

import java.util.List;

@Getter
@Setter
public class PlumberResponseDTO {
    private String name;
    private List<House> houses;
}
