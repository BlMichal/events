package com.blazek.events.mappers;

import com.blazek.events.domain.dtos.GetTicketResponseDto;
import com.blazek.events.domain.dtos.ListTicketResponseDto;
import com.blazek.events.domain.dtos.ListTicketTicketTypeResponseDto;
import com.blazek.events.domain.entities.Ticket;
import com.blazek.events.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    ListTicketTicketTypeResponseDto toListTicketTicketTypeResponseDto(TicketType ticketType);

    ListTicketResponseDto toListTicketResponseDto(Ticket ticket);

    @Mapping(target = "price", source = "ticket.ticketType.price")
    @Mapping(target = "description", source = "ticket.ticketType.description")
    @Mapping(target = "eventName", source = "ticket.ticketType.event.name")
    @Mapping(target = "eventLocation", source = "ticket.ticketType.event.eventLocation")
    @Mapping(target = "eventStart", source = "ticket.ticketType.event.eventStartDate")
    @Mapping(target = "eventEnd", source = "ticket.ticketType.event.eventEndDate")
    GetTicketResponseDto toGetTicketResponseDto(Ticket ticket);
}
