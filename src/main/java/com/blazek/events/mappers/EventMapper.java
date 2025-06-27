package com.blazek.events.mappers;

import com.blazek.events.domain.CreateEventRequest;
import com.blazek.events.domain.CreateTicketTypeRequest;
import com.blazek.events.domain.dtos.CreateEventRequestDto;
import com.blazek.events.domain.dtos.CreateEventResponseDto;
import com.blazek.events.domain.dtos.CreateTicketTypeRequestDto;
import com.blazek.events.domain.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

}
