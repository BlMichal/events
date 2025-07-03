package com.blazek.events.services;

import com.blazek.events.domain.entities.QrCode;
import com.blazek.events.domain.entities.Ticket;
import org.springframework.stereotype.Service;

@Service
public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);
}
