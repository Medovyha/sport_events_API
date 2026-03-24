package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.Player;
import com.sport_events.api.infrastructure.persistence.jpa.entity.PlayerJpaEntity;

public class PlayerMapper {

    public static Player toDomain(PlayerJpaEntity entity) {
        return new Player(
                entity.getPlayerId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getDateOfBirth());
    }
}
