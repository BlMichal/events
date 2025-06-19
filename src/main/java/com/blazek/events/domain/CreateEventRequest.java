package com.blazek.events.domain;

import com.blazek.events.domain.entities.EventStatusEnum;
import com.blazek.events.domain.entities.TicketType;
import com.blazek.events.domain.entities.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CreateEventRequest {

    private String name;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;
    private String eventLocation;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private List<CreateTicketTypeRequest> ticketTypes = new ArrayList<>();
}
