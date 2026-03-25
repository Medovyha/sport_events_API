package com.sport_events.api.application.usecase;

import com.sport_events.api.application.dto.command.RemovePlayerFromTeamCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.TeamPlayer;
import com.sport_events.api.domain.repository.TeamPlayerRepository;

public class RemovePlayerFromTeamUseCase {

    private final TeamPlayerRepository teamPlayerRepository;

    public RemovePlayerFromTeamUseCase(TeamPlayerRepository teamPlayerRepository) {
        this.teamPlayerRepository = teamPlayerRepository;
    }

    public void execute(RemovePlayerFromTeamCommand command) {
        TeamPlayer teamPlayer = teamPlayerRepository
                .findByTeamIdAndPlayerId(command.teamId(), command.playerId())
                .orElseThrow(() -> new DomainException(
                        "Player " + command.playerId() + " is not in team " + command.teamId()));
        teamPlayerRepository.deleteById(teamPlayer.getTeamPlayerId());
    }
}
