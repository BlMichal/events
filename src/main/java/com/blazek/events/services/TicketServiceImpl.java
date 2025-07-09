package com.blazek.events.services;

import com.blazek.events.domain.entities.Ticket;
import com.blazek.events.repositories.TicketRepository;
import com.blazek.events.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> listTicketForUser(UUID userId) {

        return ticketRepository.findByPurchaserId(userId);
    }
}
