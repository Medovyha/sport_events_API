package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.EventTeam;
import com.sport_events.api.infrastructure.persistence.jpa.entity.EventJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.EventTeamJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.TeamJpaEntity;

public class EventTeamMapper {

    public static EventTeam toDomain(EventTeamJpaEntity entity) {
        return new EventTeam(
                entity.getEventTeamsId(),
                entity.getEvent() != null ? entity.getEvent().getEventId() : null,
                entity.getTeam() != null ? entity.getTeam().getTeamId() : null);
    }

    public static EventTeamJpaEntity toEntity(EventTeam domain) {
        EventTeamJpaEntity entity = new EventTeamJpaEntity();
        entity.setEventTeamsId(domain.getEventTeamsId());

        if (domain.getEventId() != null) {
            EventJpaEntity event = new EventJpaEntity();
            event.setEventId(domain.getEventId());
            entity.setEvent(event);
        }

        if (domain.getTeamId() != null) {
            TeamJpaEntity team = new TeamJpaEntity();
            team.setTeamId(domain.getTeamId());
            entity.setTeam(team);
        }

        return entity;
    }
}
