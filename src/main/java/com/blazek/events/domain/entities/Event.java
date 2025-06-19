package com.blazek.events.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @Column(name = "id", updatable = false,nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    // Event start Datetime
    @Column(name = "event_start")
    private LocalDateTime eventStartDate;

    // Event end Datetime
    @Column(name = "event_end")
    private LocalDateTime eventEndDate;

    // Event location
    @Column(name = "location", nullable = false)
    private String eventLocation;

    // Event start sales date
    @Column(name = "sales_start")
    private LocalDateTime salesStart;

    // Event end sales date
    @Column(name = "sales_end")
    private LocalDateTime salesEnd;

    // Status of event
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @ManyToMany(mappedBy = "participatingEvents")
    private List<User> participants = new ArrayList<>();

    @ManyToMany(mappedBy = "staffingEvents")
    private List<User> staff = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<TicketType> ticketTypes = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at",updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(name, event.name) && Objects.equals(eventStartDate, event.eventStartDate) && Objects.equals(eventEndDate, event.eventEndDate) && Objects.equals(eventLocation, event.eventLocation) && Objects.equals(salesStart, event.salesStart) && Objects.equals(salesEnd, event.salesEnd) && status == event.status && Objects.equals(createdAt, event.createdAt) && Objects.equals(updatedAt, event.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, eventStartDate, eventEndDate, eventLocation, salesStart, salesEnd, status, createdAt, updatedAt);
    }
}

