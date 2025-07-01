package com.blazek.events.domain;

import com.blazek.events.domain.entities.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventRequest {

    private UUID id;
    private String name;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;
    private String eventLocation;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private List<UpdateTicketTypeRequest> ticketTypes;
}
