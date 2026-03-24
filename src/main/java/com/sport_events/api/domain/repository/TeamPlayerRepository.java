package com.sport_events.api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.sport_events.api.domain.model.TeamPlayer;

public interface TeamPlayerRepository {

    Optional<TeamPlayer> findById(Integer teamPlayerId);

    List<TeamPlayer> findAll();

    List<TeamPlayer> findByTeamId(Integer teamId);

    Optional<TeamPlayer> findByTeamIdAndPlayerId(Integer teamId, Integer playerId);

    TeamPlayer save(TeamPlayer teamPlayer);

    void deleteById(Integer teamPlayerId);
}
