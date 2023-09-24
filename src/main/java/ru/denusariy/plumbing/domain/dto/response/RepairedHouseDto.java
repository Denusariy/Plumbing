package ru.denusariy.plumbing.domain.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RepairedHouseDto {
    private String plumberName;
    private String houseAddress;
    private Integer repairPrice;
    private Long houseBudget;
}
