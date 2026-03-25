package com.sport_events.api.application.usecase;

import com.sport_events.api.application.dto.command.RemoveTeamFromEventCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.EventTeam;
import com.sport_events.api.domain.repository.EventRepository;
import com.sport_events.api.domain.repository.EventTeamRepository;
import com.sport_events.api.domain.repository.TeamRepository;

public class RemoveTeamFromEventUseCase {

    private final EventRepository eventRepository;
    private final TeamRepository teamRepository;
    private final EventTeamRepository eventTeamRepository;

    public RemoveTeamFromEventUseCase(
            EventRepository eventRepository,
            TeamRepository teamRepository,
            EventTeamRepository eventTeamRepository) {
        this.eventRepository = eventRepository;
        this.teamRepository = teamRepository;
        this.eventTeamRepository = eventTeamRepository;
    }

    public void execute(RemoveTeamFromEventCommand command) {
        eventRepository.findById(command.eventId())
                .orElseThrow(() -> new DomainException("Event not found: " + command.eventId()));

        teamRepository.findById(command.teamId())
                .orElseThrow(() -> new DomainException("Team not found: " + command.teamId()));

        EventTeam eventTeam = eventTeamRepository.findByEventIdAndTeamId(command.eventId(), command.teamId())
                .orElseThrow(() -> new DomainException(
                        "Team " + command.teamId() + " is not in event " + command.eventId()));

        eventTeamRepository.deleteById(eventTeam.getEventTeamsId());
    }
}
