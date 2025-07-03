package com.blazek.events.repositories;

import com.blazek.events.domain.entities.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {
}
