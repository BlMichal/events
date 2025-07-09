package com.blazek.events.controllers;

import com.blazek.events.domain.dtos.ListTicketResponseDto;
import com.blazek.events.mappers.TicketMapper;
import com.blazek.events.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    //Handles GET requests to retrieve all tickets purchased by the currently authenticated user.
    @GetMapping
    public ResponseEntity<List<ListTicketResponseDto>> listTicket(
            @AuthenticationPrincipal Jwt jwt
    ){
        UUID userId = UUID.fromString(jwt.getSubject());

        List<ListTicketResponseDto> responseDto = ticketService.listTicketForUser(userId)
                .stream()
                .map(ticketMapper::toListTicketResponseDto)
                .toList();

        return ResponseEntity.ok(responseDto);
    }
}
