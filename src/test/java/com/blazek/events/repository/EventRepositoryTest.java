package com.blazek.events.repository;

import com.blazek.events.domain.entities.Event;
import com.blazek.events.domain.entities.EventStatusEnum;
import com.blazek.events.repositories.EventRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void EventRepository_Save_ReturnsSavedEvent(){

        LocalDateTime fixedDate = LocalDateTime.of(2025, 1, 1, 12, 0);

        Event event = Event.builder()
                .name("TestEvent")
                .eventStartDate(fixedDate)
                .eventEndDate(fixedDate.plusHours(2))
                .eventLocation("Prague")
                .salesStart(fixedDate.minusDays(7))
                .salesEnd(fixedDate.minusDays(1))
                .status(EventStatusEnum.DRAFT)
                .ticketTypes(null)
                .build();

        Event savedEvent = eventRepository.save(event);

        Assertions.assertThat(savedEvent).isNotNull();
    }
}
