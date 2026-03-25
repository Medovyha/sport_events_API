package com.sport_events.api.application.usecase;

import com.sport_events.api.application.dto.command.RemovePlayerFromEventTeamCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.EventPlayer;
import com.sport_events.api.domain.model.EventTeam;
import com.sport_events.api.domain.repository.EventPlayerRepository;
import com.sport_events.api.domain.repository.EventRepository;
import com.sport_events.api.domain.repository.EventTeamRepository;
import com.sport_events.api.domain.repository.TeamRepository;

public class RemovePlayerFromEventTeamUseCase {

    private final EventRepository eventRepository;
    private final TeamRepository teamRepository;
    private final EventTeamRepository eventTeamRepository;
    private final EventPlayerRepository eventPlayerRepository;

    public RemovePlayerFromEventTeamUseCase(
            EventRepository eventRepository,
            TeamRepository teamRepository,
            EventTeamRepository eventTeamRepository,
            EventPlayerRepository eventPlayerRepository) {
        this.eventRepository = eventRepository;
        this.teamRepository = teamRepository;
        this.eventTeamRepository = eventTeamRepository;
        this.eventPlayerRepository = eventPlayerRepository;
    }

    public void execute(RemovePlayerFromEventTeamCommand command) {
        eventRepository.findById(command.eventId())
                .orElseThrow(() -> new DomainException("Event not found: " + command.eventId()));

        teamRepository.findById(command.teamId())
                .orElseThrow(() -> new DomainException("Team not found: " + command.teamId()));

        EventTeam eventTeam = eventTeamRepository.findByEventIdAndTeamId(command.eventId(), command.teamId())
                .orElseThrow(() -> new DomainException(
                        "Team " + command.teamId() + " is not added to event " + command.eventId()));

        EventPlayer eventPlayer = eventPlayerRepository
                .findByEventTeamIdAndPlayerId(eventTeam.getEventTeamsId(), command.playerId())
                .orElseThrow(() -> new DomainException(
                        "Player " + command.playerId() + " is not in team " + command.teamId()
                                + " for event " + command.eventId()));

        eventPlayerRepository.deleteById(eventPlayer.getEventPlayersId());
    }
}
