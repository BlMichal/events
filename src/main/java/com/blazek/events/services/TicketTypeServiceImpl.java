package com.blazek.events.services;

import com.blazek.events.domain.entities.Ticket;
import com.blazek.events.domain.entities.TicketType;
import com.blazek.events.domain.entities.User;
import com.blazek.events.exceptions.TicketTypeNotFoundException;
import com.blazek.events.exceptions.UserNotFoundException;
import com.blazek.events.repositories.QrCodeRepository;
import com.blazek.events.repositories.TicketRepository;
import com.blazek.events.repositories.TicketTypeRepository;
import com.blazek.events.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QrCodeRepository qrCodeRepository;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(String.format("User with ID '%s' was not found", userId)));
        TicketType ticketType = ticketTypeRepository.findById(ticketTypeId).orElseThrow(() -> new TicketTypeNotFoundException(String.format("Ticket type with ID '%s' was not found", ticketTypeId)));

        ticketRepository.countByTicketTypeId(ticketType.getId());

        return null;
    }
}
