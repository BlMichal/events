package com.blazek.events.services;

import com.blazek.events.domain.CreateEventRequest;
import com.blazek.events.domain.UpdateEventRequest;
import com.blazek.events.domain.entities.Event;
import com.blazek.events.domain.entities.EventStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    void deleteEvent(UUID id, UUID organizerId);

    Page<Event> listOfEvents(EventStatusEnum status, Pageable pageable);

    Page<Event> searchListOfEvents(String query, Pageable pageable);

    Optional<Event> getEvent(UUID id);


}
