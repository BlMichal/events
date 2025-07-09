package com.blazek.events.services;

import com.blazek.events.domain.entities.QrCode;
import com.blazek.events.domain.entities.Ticket;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
