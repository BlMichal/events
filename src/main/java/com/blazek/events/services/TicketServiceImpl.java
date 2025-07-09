package com.blazek.events.services;

import com.blazek.events.domain.entities.Ticket;
import com.blazek.events.exceptions.TicketNotFoundException;
import com.blazek.events.exceptions.UserNotFoundException;
import com.blazek.events.repositories.TicketRepository;
import com.blazek.events.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> listTicketForUser(UUID userId) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }

        return ticketRepository.findByPurchaserId(userId);
    }

    @Override
    public Optional<Ticket> getTicketForUser(UUID userId, UUID ticketId) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }

        if (!ticketRepository.existsById(ticketId)) {
            throw new TicketNotFoundException("Ticket not found");
        }

        return ticketRepository.findByIdAndPurchaserId(userId,ticketId);
    }
}
