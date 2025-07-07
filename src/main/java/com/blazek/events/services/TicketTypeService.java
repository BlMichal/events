package com.blazek.events.services;

import com.blazek.events.domain.entities.Ticket;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface TicketTypeService {
    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}
