package com.blazek.events.mappers;

import com.blazek.events.domain.CreateEventRequest;
import com.blazek.events.domain.CreateTicketTypeRequest;
import com.blazek.events.domain.UpdateEventRequest;
import com.blazek.events.domain.UpdateTicketTypeRequest;
import com.blazek.events.domain.dtos.*;
import com.blazek.events.domain.entities.Event;
import com.blazek.events.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

    ListEventTicketTypeResponseDto toListTicketTypeResponseDto(TicketType ticketType);

    ListEventResponseDto toListEventResponseDto(Event event);

    GetEventResponseDto toGetEventResponseDto(Event event);

    GetTicketTypeResponseDto toGetTicketTypeResponseDto(TicketType ticketType);

    UpdateTicketTypeRequest fromDto(UpdateTicketTypeRequestDto dto);

    UpdateEventRequest fromDto(UpdateEventRequestDto dto);

    UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticketType);

    UpdateEventResponseDto toUpdateEventResponseDto(Event event);

    ListOfEventsResponseDto toListOfEventsResponseDto(Event event);

}
