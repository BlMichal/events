package com.blazek.events.domain.dtos;

import com.blazek.events.domain.entities.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTicketResponseDto {

        private UUID id;
        private TicketStatusEnum status;
        private Double price;
        private String description;
        private String eventName;
        private String eventLocation;
        private LocalDateTime eventStart;
        private LocalDateTime eventEnd;
}
