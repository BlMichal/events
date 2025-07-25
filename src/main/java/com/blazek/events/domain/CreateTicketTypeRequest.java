package com.blazek.events.domain;

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
    private Integer totalAvailable;
}
