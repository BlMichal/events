package com.blazek.events.services;

import com.blazek.events.domain.entities.Ticket;
import com.blazek.events.domain.entities.TicketStatusEnum;
import com.blazek.events.domain.entities.TicketType;
import com.blazek.events.domain.entities.User;
import com.blazek.events.exceptions.TicketSoldOutException;
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
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        // Get user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with ID '%s' was not found", userId)));

        // Get ticket type with lock
        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
                .orElseThrow(() -> new TicketTypeNotFoundException(String.format("Ticket type with ID '%s' was not found", ticketTypeId)));

        // Total ticket sold count by Ticket ID
        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());

        // Total ticket available
        Integer totalAvailable = ticketType.getTotalAvailable();

        if (purchasedTickets + 1 > totalAvailable) {
            throw new TicketSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        // Generate QRCODE with ticket data
        qrCodeService.generateQrCode(ticket);

        return ticketRepository.save(ticket);
    }
}
