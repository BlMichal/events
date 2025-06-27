package com.blazek.events.controllers;

import com.blazek.events.domain.CreateEventRequest;
import com.blazek.events.domain.dtos.CreateEventRequestDto;
import com.blazek.events.domain.dtos.CreateEventResponseDto;
import com.blazek.events.domain.entities.Event;
import com.blazek.events.mappers.EventMapper;
import com.blazek.events.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(params = "/api/v1/events")
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
}
