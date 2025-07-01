package com.blazek.events.services;

import com.blazek.events.domain.CreateEventRequest;
import com.blazek.events.domain.UpdateEventRequest;
import com.blazek.events.domain.entities.Event;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface EventService {

    Event createEvent(UUID organizerId, CreateEventRequest event);

    List<Event> getEventsForOrganizer(UUID organizerId);

    Optional<Event> getEventForOrganizer(UUID id, UUID organizerId);

    Event updateEventForOrganizer(UUID id, UUID organizerId, UpdateEventRequest event);
}
