package com.blazek.events.services;

import com.blazek.events.domain.CreateEventRequest;
import com.blazek.events.domain.UpdateEventRequest;
import com.blazek.events.domain.UpdateTicketTypeRequest;
import com.blazek.events.domain.entities.Event;
import com.blazek.events.domain.entities.EventStatusEnum;
import com.blazek.events.domain.entities.TicketType;
import com.blazek.events.domain.entities.User;
import com.blazek.events.exceptions.EventNotFoundException;
import com.blazek.events.exceptions.EventUpdateException;
import com.blazek.events.exceptions.TicketTypeNotFoundException;
import com.blazek.events.exceptions.UserNotFoundException;
import com.blazek.events.repositories.EventRepository;
import com.blazek.events.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Transactional
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
    @Transactional
    public Event updateEventForOrganizer(UUID id, UUID organizerId, UpdateEventRequest event) {

        if (null == event.getId()) throw new EventUpdateException("Event ID cannot be null");

        if (!id.equals(event.getId())) throw new EventUpdateException("Cannot update the ID of an event");

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

        /*
        Set of TickerType Ids that already exists
         */
        Set<UUID> requestTicketTypeIds = event.getTicketTypes()
                .stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        /*
        Deleting old TicketTypes that was not send back in request
         */
        existingEvent.getTicketTypes()
                .removeIf(existingTicketType -> !requestTicketTypeIds.contains(existingTicketType.getId()));

        /*
        Creating MAP for fast update by UUID of existing TicketTypes
         */
        Map<UUID, TicketType> existingTicketTypesMap = existingEvent.getTicketTypes().stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity()));


        for (UpdateTicketTypeRequest ticketType : event.getTicketTypes())
            if (null == ticketType.getId()) {
                // CREATE NEW TICKET TYPE WHEN ADDED
                TicketType ticketTypeToCreate = new TicketType();
                ticketTypeToCreate.setName(ticketType.getName());
                ticketTypeToCreate.setPrice(ticketType.getPrice());
                ticketTypeToCreate.setDescription(ticketType.getDescription());
                ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                ticketTypeToCreate.setEvent(existingEvent);

                existingEvent.getTicketTypes().add(ticketTypeToCreate);

            } else if (existingTicketTypesMap.containsKey(ticketType.getId())) {
                // UPDATE EXISTING EVENT
                TicketType existingTicketType = existingTicketTypesMap.get(ticketType.getId());
                existingTicketType.setName(ticketType.getName());
                existingTicketType.setPrice(ticketType.getPrice());
                existingTicketType.setDescription(ticketType.getDescription());
                existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());
            } else {
                throw new TicketTypeNotFoundException(String.format(
                        "Ticket type with ID '%s' don't not exists", ticketType.getId()));
            }


        return eventRepository.save(existingEvent);
    }

    @Override
    @Transactional
    public void deleteEvent(UUID id, UUID organizerId) {

        // We have method where we're already searching for event, this help add some abstraction for code.
        getEventForOrganizer(id,organizerId).ifPresent(event -> eventRepository.delete(event) );

        //eventRepository.deleteEventByIdAndOrganizerId(id, organizerId);
    }

    @Override
    public Page<Event> listOfEvents(EventStatusEnum status,Pageable pageable) {
        return eventRepository.findByStatus(status, pageable);
    }

    @Override
    public Page<Event> searchListOfEvents(String query, Pageable pageable) {
       return eventRepository.searchEvents(query,pageable);
    }

    @Override
    public Optional<Event> getEvent(UUID id) {
        return eventRepository.findById(id);
    }

}
