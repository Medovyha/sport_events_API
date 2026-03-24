package com.sport_events.api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.sport_events.api.domain.model.EventPlayer;

public interface EventPlayerRepository {

    Optional<EventPlayer> findById(Integer eventPlayersId);

    List<EventPlayer> findAll();

    List<EventPlayer> findByEventTeamId(Integer eventTeamId);

    Optional<EventPlayer> findByEventTeamIdAndPlayerId(Integer eventTeamId, Integer playerId);

    EventPlayer save(EventPlayer eventPlayer);

    void deleteById(Integer eventPlayersId);
}
