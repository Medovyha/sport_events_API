package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.Event;
import com.sport_events.api.infrastructure.persistence.jpa.entity.EventJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.VenueJpaEntity;

public class EventMapper {

    public static Event toDomain(EventJpaEntity entity) {
        return new Event(
                entity.getEventId(),
                entity.getStartsAt(),
                entity.getVenue() != null ? entity.getVenue().getVenueId() : null);
    }

    public static EventJpaEntity toEntity(Event domain, VenueJpaEntity venueEntity) {
        EventJpaEntity entity = new EventJpaEntity();
        entity.setEventId(domain.getEventId());
        entity.setStartsAt(domain.getStartsAt());
        entity.setVenue(venueEntity);
        return entity;
    }
}
