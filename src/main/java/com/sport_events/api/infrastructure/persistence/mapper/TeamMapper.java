package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.Team;
import com.sport_events.api.infrastructure.persistence.jpa.entity.TeamJpaEntity;

public class TeamMapper {

    public static Team toDomain(TeamJpaEntity entity) {
        return new Team(
                entity.getTeamId(),
                entity.getName(),
                entity.getSport() != null ? entity.getSport().getSportId() : null);
    }
}
