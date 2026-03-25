package com.sport_events.api.application.usecase;

import com.sport_events.api.application.dto.command.CreateTeamCommand;
import com.sport_events.api.application.dto.result.TeamResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Sport;
import com.sport_events.api.domain.model.Team;
import com.sport_events.api.domain.repository.SportRepository;
import com.sport_events.api.domain.repository.TeamRepository;

public class CreateTeamUseCase {

    private final TeamRepository teamRepository;
    private final SportRepository sportRepository;

    public CreateTeamUseCase(TeamRepository teamRepository, SportRepository sportRepository) {
        this.teamRepository = teamRepository;
        this.sportRepository = sportRepository;
    }

    public TeamResult execute(CreateTeamCommand command) {
        Sport sport = sportRepository.findById(command.sportId())
                .orElseThrow(() -> new DomainException("Sport not found: " + command.sportId()));
        Team team = new Team(null, command.name(), sport.getSportId());
        Team saved = teamRepository.save(team);
        return new TeamResult(saved.getTeamId(), saved.getName(), saved.getSportId(), null);
    }
}
