package com.blazek.events.repositories;

import com.blazek.events.domain.entities.Event;
import com.blazek.events.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findByOrganizerId(UUID userId);

    Optional<Event> findByIdAndOrganizerId(UUID id, UUID userId);
}
