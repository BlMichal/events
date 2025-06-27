package com.blazek.events.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketTypeRequestDto {

    @NotBlank(message = "Jméno nesmí být prázdné")
    private String name;

    private String description;

    @NotNull(message = "Cena musí být zadána")
    @PositiveOrZero(message = "Cena musí být 0 nebo více")
    private Double price;

    private Integer totalAvailable;
}
