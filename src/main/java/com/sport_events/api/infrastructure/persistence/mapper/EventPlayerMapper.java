package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.EventPlayer;
import com.sport_events.api.infrastructure.persistence.jpa.entity.EventPlayerJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.EventTeamJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.PlayerJpaEntity;

public class EventPlayerMapper {

    public static EventPlayer toDomain(EventPlayerJpaEntity entity) {
        return new EventPlayer(
                entity.getEventPlayersId(),
                entity.getEventTeam() != null ? entity.getEventTeam().getEventTeamsId() : null,
                entity.getPlayer() != null ? entity.getPlayer().getPlayerId() : null);
    }

    public static EventPlayerJpaEntity toEntity(EventPlayer domain) {
        EventPlayerJpaEntity entity = new EventPlayerJpaEntity();
        entity.setEventPlayersId(domain.getEventPlayersId());

        if (domain.getEventTeamId() != null) {
            EventTeamJpaEntity eventTeam = new EventTeamJpaEntity();
            eventTeam.setEventTeamsId(domain.getEventTeamId());
            entity.setEventTeam(eventTeam);
        }

        if (domain.getPlayerId() != null) {
            PlayerJpaEntity player = new PlayerJpaEntity();
            player.setPlayerId(domain.getPlayerId());
            entity.setPlayer(player);
        }

        return entity;
    }
}
