package com.sport_events.api.application.usecase;

import com.sport_events.api.application.dto.command.UpdateTeamCommand;
import com.sport_events.api.application.dto.result.TeamResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Team;
import com.sport_events.api.domain.repository.SportRepository;
import com.sport_events.api.domain.repository.TeamRepository;

public class UpdateTeamUseCase {

    private final TeamRepository teamRepository;
    private final SportRepository sportRepository;

    public UpdateTeamUseCase(TeamRepository teamRepository, SportRepository sportRepository) {
        this.teamRepository = teamRepository;
        this.sportRepository = sportRepository;
    }

    public TeamResult execute(UpdateTeamCommand command) {
        teamRepository.findById(command.teamId())
                .orElseThrow(() -> new DomainException("Team not found: " + command.teamId()));
        sportRepository.findById(command.sportId())
                .orElseThrow(() -> new DomainException("Sport not found: " + command.sportId()));
        Team team = new Team(command.teamId(), command.name(), command.sportId());
        Team saved = teamRepository.save(team);
        return new TeamResult(saved.getTeamId(), saved.getName(), saved.getSportId(), null);
    }

    public void deleteById(Integer teamId) {
        teamRepository.findById(teamId)
                .orElseThrow(() -> new DomainException("Team not found: " + teamId));
        teamRepository.deleteById(teamId);
    }
}
