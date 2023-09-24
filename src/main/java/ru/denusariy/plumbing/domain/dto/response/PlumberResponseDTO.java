package ru.denusariy.plumbing.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlumberResponseDTO {
    private String name;
    @Builder.Default
    private Long bankAccount = 0L;
    private List<HouseResponseDTO> houses;
}
