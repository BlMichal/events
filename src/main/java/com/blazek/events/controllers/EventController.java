package com.blazek.events.controllers;

import com.blazek.events.domain.CreateEventRequest;
import com.blazek.events.domain.dtos.CreateEventRequestDto;
import com.blazek.events.domain.dtos.CreateEventResponseDto;
import com.blazek.events.domain.dtos.ListEventResponseDto;
import com.blazek.events.domain.entities.Event;
import com.blazek.events.mappers.EventMapper;
import com.blazek.events.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ListEventResponseDto> getEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID id
    ){
        UUID userId = UUID.fromString(jwt.getSubject());
        return eventService.getEventForOrganizer(id, userId)
                .map(event -> eventMapper.toListEventResponseDto(event))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());


    };
}
