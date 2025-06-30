package com.blazek.events.services;

import com.blazek.events.domain.CreateEventRequest;
import com.blazek.events.domain.entities.Event;
import com.blazek.events.domain.entities.TicketType;
import com.blazek.events.domain.entities.User;
import com.blazek.events.exceptions.UserNotFoundException;
import com.blazek.events.repositories.EventRepository;
import com.blazek.events.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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


}
