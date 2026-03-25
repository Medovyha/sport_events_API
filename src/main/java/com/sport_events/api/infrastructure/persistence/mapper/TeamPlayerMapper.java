package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.TeamPlayer;
import com.sport_events.api.infrastructure.persistence.jpa.entity.TeamPlayerJpaEntity;

public class TeamPlayerMapper {

    public static TeamPlayer toDomain(TeamPlayerJpaEntity entity) {
        return new TeamPlayer(
                entity.getTeamPlayerId(),
                entity.getTeam() != null ? entity.getTeam().getTeamId() : null,
                entity.getPlayer() != null ? entity.getPlayer().getPlayerId() : null);
    }
}
