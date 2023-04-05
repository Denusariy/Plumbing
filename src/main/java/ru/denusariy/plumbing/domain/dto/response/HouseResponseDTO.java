package ru.denusariy.plumbing.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HouseResponseDTO {
    private String address;
    private PlumberResponseDTO plumber;

    public String getPlumber() {
        if(plumber == null)
            return null;
        return plumber.getName();
    }
}
