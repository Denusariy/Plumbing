package ru.denusariy.plumbing.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseResponseDTO {
    private String address;
    private PlumberResponseDTO plumber;
}
