package com.blazek.events.mappers;

import com.blazek.events.domain.dtos.ListTicketResponseDto;
import com.blazek.events.domain.dtos.ListTicketTicketTypeResponseDto;
import com.blazek.events.domain.entities.Ticket;
import com.blazek.events.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    ListTicketTicketTypeResponseDto toListTicketTicketTypeResponseDto(TicketType ticketType);

    ListTicketResponseDto toListTicketResponseDto(Ticket ticket);
}
