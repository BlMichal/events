package com.blazek.events.domain;

import com.blazek.events.domain.entities.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketTypeRequest {

    private String name;
    private String description;
    private Double price;
    private int totalAvailable;
}
