package ru.denusariy.plumbing.domain.dto.request;

import lombok.Data;

@Data
public class AssignPlumberDto {
    private Integer plumberId;
    private Long houseId;
}
