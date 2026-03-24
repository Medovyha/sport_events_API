package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.EventTeam;
import com.sport_events.api.infrastructure.persistence.jpa.entity.EventTeamJpaEntity;

public class EventTeamMapper {

    public static EventTeam toDomain(EventTeamJpaEntity entity) {
        return new EventTeam(
                entity.getEventTeamsId(),
                entity.getEvent() != null ? entity.getEvent().getEventId() : null,
                entity.getTeam() != null ? entity.getTeam().getTeamId() : null);
    }
}
