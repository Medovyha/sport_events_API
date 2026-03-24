package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.Venue;
import com.sport_events.api.infrastructure.persistence.jpa.entity.VenueJpaEntity;

public class VenueMapper {

    public static Venue toDomain(VenueJpaEntity entity) {
        return new Venue(entity.getVenueId(), entity.getName(), entity.getAddress());
    }

    public static VenueJpaEntity toEntity(Venue domain) {
        VenueJpaEntity entity = new VenueJpaEntity();
        entity.setVenueId(domain.getVenueId());
        entity.setName(domain.getName());
        entity.setAddress(domain.getAddress());
        return entity;
    }
}
