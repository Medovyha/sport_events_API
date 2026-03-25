package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.Team;
import com.sport_events.api.infrastructure.persistence.jpa.entity.SportJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.TeamJpaEntity;

public class TeamMapper {

    public static Team toDomain(TeamJpaEntity entity) {
        return new Team(
                entity.getTeamId(),
                entity.getName(),
                entity.getSport() != null ? entity.getSport().getSportId() : null);
    }

    public static TeamJpaEntity toEntity(Team team) {
        TeamJpaEntity entity = new TeamJpaEntity();
        entity.setTeamId(team.getTeamId());
        entity.setName(team.getName());
        if (team.getSportId() != null) {
            SportJpaEntity sport = new SportJpaEntity();
            sport.setSportId(team.getSportId());
            entity.setSport(sport);
        }
        return entity;
    }
}
