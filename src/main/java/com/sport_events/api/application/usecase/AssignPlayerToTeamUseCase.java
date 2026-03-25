package com.sport_events.api.application.usecase;

import com.sport_events.api.application.dto.command.AssignPlayerToTeamCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.TeamPlayer;
import com.sport_events.api.domain.repository.PlayerRepository;
import com.sport_events.api.domain.repository.TeamPlayerRepository;
import com.sport_events.api.domain.repository.TeamRepository;

public class AssignPlayerToTeamUseCase {

    private final TeamPlayerRepository teamPlayerRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public AssignPlayerToTeamUseCase(
            TeamPlayerRepository teamPlayerRepository,
            TeamRepository teamRepository,
            PlayerRepository playerRepository) {
        this.teamPlayerRepository = teamPlayerRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public void execute(AssignPlayerToTeamCommand command) {
        teamRepository.findById(command.teamId())
                .orElseThrow(() -> new DomainException("Team not found: " + command.teamId()));
        playerRepository.findById(command.playerId())
                .orElseThrow(() -> new DomainException("Player not found: " + command.playerId()));
        if (teamPlayerRepository.findByTeamIdAndPlayerId(command.teamId(), command.playerId()).isPresent()) {
            throw new DomainException("Player " + command.playerId()
                    + " is already assigned to team " + command.teamId());
        }
        teamPlayerRepository.save(new TeamPlayer(null, command.teamId(), command.playerId()));
    }
}
