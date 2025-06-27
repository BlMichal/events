package com.blazek.events.domain.dtos;

import com.blazek.events.domain.entities.EventStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequestDto {

    @NotBlank(message = "Jméno nesmí být prázdné")
    private String name;

    private LocalDateTime eventStartDate;

    private LocalDateTime eventEndDate;

    @NotBlank(message = "Místo nesmí být prázdné")
    private String eventLocation;

    private LocalDateTime salesStart;

    private LocalDateTime salesEnd;

    @NotBlank(message = "Status údálosti musí být uveden")
    private EventStatusEnum status;

    @NotEmpty(message = "Aspoň 1 typ vstupenky musí být uveden")
    @Valid
    private List<CreateTicketTypeRequestDto> ticketType;
}
