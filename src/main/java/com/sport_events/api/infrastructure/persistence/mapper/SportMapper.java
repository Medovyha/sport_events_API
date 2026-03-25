package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.Sport;
import com.sport_events.api.infrastructure.persistence.jpa.entity.SportJpaEntity;

public class SportMapper {

    public static Sport toDomain(SportJpaEntity entity) {
        return new Sport(entity.getSportId());
    }
}
