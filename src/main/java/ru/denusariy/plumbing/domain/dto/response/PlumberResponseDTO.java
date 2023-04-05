package ru.denusariy.plumbing.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlumberResponseDTO {
    private String name;
    private List<HouseResponseDTO> houses;
}
