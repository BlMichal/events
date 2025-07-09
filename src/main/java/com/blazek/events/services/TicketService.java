package com.blazek.events.services;

import com.blazek.events.domain.entities.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface TicketService {
    List<Ticket> listTicketForUser(UUID userId);
    Optional<Ticket> getTicketForUser(UUID userId, UUID ticketId);
}
