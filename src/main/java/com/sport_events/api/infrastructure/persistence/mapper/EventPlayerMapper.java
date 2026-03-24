package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.EventPlayer;
import com.sport_events.api.infrastructure.persistence.jpa.entity.EventPlayerJpaEntity;

public class EventPlayerMapper {

    public static EventPlayer toDomain(EventPlayerJpaEntity entity) {
        return new EventPlayer(
                entity.getEventPlayersId(),
                entity.getEventTeam() != null ? entity.getEventTeam().getEventTeamsId() : null,
                entity.getPlayer() != null ? entity.getPlayer().getPlayerId() : null);
    }
}
