package com.blazek.events.services;

import com.blazek.events.domain.CreateEventRequest;
import com.blazek.events.domain.UpdateEventRequest;
import com.blazek.events.domain.UpdateTicketTypeRequest;
import com.blazek.events.domain.entities.Event;
import com.blazek.events.domain.entities.TicketType;
import com.blazek.events.domain.entities.User;
import com.blazek.events.exceptions.EventNotFoundException;
import com.blazek.events.exceptions.UserNotFoundException;
import com.blazek.events.repositories.EventRepository;
import com.blazek.events.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest event) {
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with ID '%s' not found", organizerId)));

        Event eventToCreate = new Event();

        eventToCreate.setName(event.getName());
        eventToCreate.setEventStartDate(event.getEventStartDate());
        eventToCreate.setEventEndDate(event.getEventEndDate());
        eventToCreate.setEventLocation(event.getEventLocation());
        eventToCreate.setSalesStart(event.getSalesStart());
        eventToCreate.setSalesEnd(event.getSalesEnd());
        eventToCreate.setStatus(event.getStatus());
        eventToCreate.setOrganizer(organizer);

        List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(ticketType ->{
            TicketType ticketTypeToCreate = new TicketType();
            ticketTypeToCreate.setName(ticketType.getName());
            ticketTypeToCreate.setPrice(ticketType.getPrice());
            ticketTypeToCreate.setDescription(ticketType.getDescription());
            ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
            ticketTypeToCreate.setEvent(eventToCreate);

            return ticketTypeToCreate;
        }).toList();

        eventToCreate.setTicketTypes(ticketTypesToCreate);

        return eventRepository.save(eventToCreate);
    }

    @Override
    public List<Event> getEventsForOrganizer(UUID organizerId) {
        return eventRepository.findByOrganizerId(organizerId) ;
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID id, UUID organizerId) {
        return eventRepository.findByIdAndOrganizerId(id, organizerId);
    }

    @Override
    public Event updateEventForOrganizer(UUID id, UUID organizerId, UpdateEventRequest event) {

        if(null == event.getId()) throw new EventNotFoundException("Event ID cannot be null");

        if(!id.equals(event.getId())) throw new EventNotFoundException("Cannot update the ID of an event");

        Event existingEvent = eventRepository.findByIdAndOrganizerId(id, organizerId)
                .orElseThrow(() -> new EventNotFoundException(
                        String.format("Event with ID '%s' does not exist", id))
                );

        existingEvent.setName(event.getName());
        existingEvent.setEventStartDate(event.getEventStartDate());
        existingEvent.setEventEndDate(event.getEventEndDate());
        existingEvent.setEventLocation(event.getEventLocation());
        existingEvent.setSalesStart(event.getSalesStart());
        existingEvent.setSalesEnd(event.getSalesEnd());
        existingEvent.setStatus(event.getStatus());

        return eventRepository.save(existingEvent);

    }


}
