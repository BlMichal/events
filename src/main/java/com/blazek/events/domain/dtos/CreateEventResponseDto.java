package com.blazek.events.domain.dtos;

import com.blazek.events.domain.entities.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventResponseDto {
    private String name;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;
    private String eventLocation;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private List<CreateTicketTypeRequestDto> ticketType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
