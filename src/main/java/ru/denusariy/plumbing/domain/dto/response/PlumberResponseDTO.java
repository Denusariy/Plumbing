package ru.denusariy.plumbing.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlumberResponseDTO {
    private String name;
    private List<HouseResponseDTO> houses;
}
