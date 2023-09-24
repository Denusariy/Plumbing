package ru.denusariy.plumbing.domain.dto.request;

import lombok.Data;

@Data
public class HouseToRepairDto {
    private Long houseId;
    private Integer plumberId;
    private Integer repairPrice;
}
