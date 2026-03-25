package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.TeamPlayer;
import com.sport_events.api.infrastructure.persistence.jpa.entity.PlayerJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.TeamJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.TeamPlayerJpaEntity;

public class TeamPlayerMapper {

    public static TeamPlayer toDomain(TeamPlayerJpaEntity entity) {
        return new TeamPlayer(
                entity.getTeamPlayerId(),
                entity.getTeam() != null ? entity.getTeam().getTeamId() : null,
                entity.getPlayer() != null ? entity.getPlayer().getPlayerId() : null);
    }

    public static TeamPlayerJpaEntity toEntity(TeamPlayer tp) {
        TeamPlayerJpaEntity entity = new TeamPlayerJpaEntity();
        if (tp.getTeamId() != null) {
            TeamJpaEntity team = new TeamJpaEntity();
            team.setTeamId(tp.getTeamId());
            entity.setTeam(team);
        }
        if (tp.getPlayerId() != null) {
            PlayerJpaEntity player = new PlayerJpaEntity();
            player.setPlayerId(tp.getPlayerId());
            entity.setPlayer(player);
        }
        return entity;
    }
}
