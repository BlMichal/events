package com.blazek.events.repositories;

import com.blazek.events.domain.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    int countByTicketTypeId(UUID ticketTypeId);

    List<Ticket> findByPurchaserId(UUID purchaserId);

    Optional<Ticket> findByIdAndPurchaserId(UUID id,UUID purchaserId);
}
