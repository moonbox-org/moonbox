package com.moonbox.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasuringUnitRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String symbol;
    private String description;
}
