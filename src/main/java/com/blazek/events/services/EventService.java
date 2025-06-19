package com.blazek.events.services;

import com.blazek.events.domain.CreateEventRequest;
import com.blazek.events.domain.entities.Event;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface EventService {

    Event createEvent(UUID organizerId, CreateEventRequest event);

}
