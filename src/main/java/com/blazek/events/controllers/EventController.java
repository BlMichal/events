package com.blazek.events.controllers;

import com.blazek.events.domain.CreateEventRequest;
import com.blazek.events.domain.UpdateEventRequest;
import com.blazek.events.domain.dtos.*;
import com.blazek.events.domain.entities.Event;
import com.blazek.events.domain.entities.EventStatusEnum;
import com.blazek.events.mappers.EventMapper;
import com.blazek.events.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventMapper eventMapper;
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto){
        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);
        UUID userId = UUID.fromString(jwt.getSubject());

        Event createdEvent = eventService.createEvent(userId,createEventRequest);

        return new ResponseEntity<>(eventMapper.toDto(createdEvent), HttpStatus.CREATED);
   }

    @PutMapping("/{eventId}")
    public ResponseEntity<UpdateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @Valid @RequestBody UpdateEventRequestDto updateEventRequestDto){
        UpdateEventRequest updateEventRequest = eventMapper.fromDto(updateEventRequestDto);
        UUID userId = UUID.fromString(jwt.getSubject());

        Event updatedEvent = eventService.updateEventForOrganizer(eventId,userId,updateEventRequest);

        return ResponseEntity.ok(eventMapper.toUpdateEventResponseDto(updatedEvent));
    }

    @GetMapping
    public ResponseEntity<List<ListEventResponseDto>> getEvents(
            @AuthenticationPrincipal Jwt jwt
        ){
        UUID userId = UUID.fromString(jwt.getSubject());
        List<Event> events = eventService.getEventsForOrganizer(userId);

        List<ListEventResponseDto> response = events.stream()
                .map(event -> eventMapper.toListEventResponseDto(event))
                .toList();

        return ResponseEntity.ok(response);
    };

    @GetMapping("/{id}")
    public ResponseEntity<GetEventResponseDto> getEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID id
    ){
        UUID userId = UUID.fromString(jwt.getSubject());
        return eventService.getEventForOrganizer(id, userId)
                .map(event -> eventMapper.toGetEventResponseDto(event))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    };

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID id
    ){
        UUID userId = UUID.fromString(jwt.getSubject());
        eventService.deleteEvent(id, userId);

        return ResponseEntity.noContent().build();
    };

    /**
     * Public endpoint with no auth
     * @param status (Enum DRAFT, PUBLISHED,...)
     * @param pageable Pageable for sorting
     * @return Event pages with specific status of events
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<ListOfEventsResponseDto>> ListPublicEvents(
            @RequestParam(required = false) String q,
            @PathVariable EventStatusEnum status,
            Pageable pageable
    ){
        Page<Event> events;
        if(q != null && !q.trim().isEmpty()) events = eventService.searchListOfEvents(q, pageable);
        else events = eventService.listOfEvents(status, pageable);

        Page<ListOfEventsResponseDto> eventResponse = events.map(event -> eventMapper.toListOfEventsResponseDto(event));

        return ResponseEntity.ok(eventResponse);
    };

    /***
     * Public endpoint for details info of event
     * @param id UUID of event
     * @return Event details
     */
    @GetMapping("/event-details/{id}")
    public ResponseEntity<GetEventResponseDto> getPublicEvent(
            @PathVariable UUID id
    ){
        return eventService.getEvent(id)
                .map(event -> eventMapper.toGetEventResponseDto(event))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    };
}
