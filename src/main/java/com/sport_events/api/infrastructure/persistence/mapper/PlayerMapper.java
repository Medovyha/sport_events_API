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

    public static PlayerJpaEntity toEntity(Player player) {
        PlayerJpaEntity entity = new PlayerJpaEntity();
        entity.setPlayerId(player.getPlayerId());
        entity.setFirstName(player.getFirstName());
        entity.setLastName(player.getLastName());
        entity.setDateOfBirth(player.getDateOfBirth());
        return entity;
    }
}
