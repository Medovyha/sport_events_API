package com.sport_events.api.application.usecase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sport_events.api.application.dto.command.AddPlayerToEventTeamCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.EventPlayer;
import com.sport_events.api.domain.model.EventTeam;
import com.sport_events.api.domain.repository.EventPlayerRepository;
import com.sport_events.api.domain.repository.EventRepository;
import com.sport_events.api.domain.repository.EventTeamRepository;
import com.sport_events.api.domain.repository.PlayerRepository;
import com.sport_events.api.domain.repository.TeamPlayerRepository;
import com.sport_events.api.domain.repository.TeamRepository;

public class AddPlayerToEventTeamUseCase {

    private final EventRepository eventRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final TeamPlayerRepository teamPlayerRepository;
    private final EventTeamRepository eventTeamRepository;
    private final EventPlayerRepository eventPlayerRepository;

    public AddPlayerToEventTeamUseCase(
            EventRepository eventRepository,
            TeamRepository teamRepository,
            PlayerRepository playerRepository,
            TeamPlayerRepository teamPlayerRepository,
            EventTeamRepository eventTeamRepository,
            EventPlayerRepository eventPlayerRepository) {
        this.eventRepository = eventRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.teamPlayerRepository = teamPlayerRepository;
        this.eventTeamRepository = eventTeamRepository;
        this.eventPlayerRepository = eventPlayerRepository;
    }

    public void execute(AddPlayerToEventTeamCommand command) {
                List<Integer> playerIds = command.playerIds();
                if (playerIds == null || playerIds.isEmpty()) {
                        throw new DomainException("At least one player is required");
                }

        eventRepository.findById(command.eventId())
                .orElseThrow(() -> new DomainException("Event not found: " + command.eventId()));

        teamRepository.findById(command.teamId())
                .orElseThrow(() -> new DomainException("Team not found: " + command.teamId()));

        EventTeam eventTeam = eventTeamRepository.findByEventIdAndTeamId(command.eventId(), command.teamId())
                .orElseThrow(() -> new DomainException(
                        "Team " + command.teamId() + " is not added to event " + command.eventId()));

                Set<Integer> uniquePlayerIds = new HashSet<>();
                for (Integer playerId : playerIds) {
                        if (!uniquePlayerIds.add(playerId)) {
                                throw new DomainException("Duplicate player in request: " + playerId);
                        }

                        playerRepository.findById(playerId)
                                        .orElseThrow(() -> new DomainException("Player not found: " + playerId));

                        if (teamPlayerRepository.findByTeamIdAndPlayerId(command.teamId(), playerId).isEmpty()) {
                                throw new DomainException("Player " + playerId + " is not assigned to team " + command.teamId());
                        }

                        if (eventPlayerRepository.findByEventTeamIdAndPlayerId(eventTeam.getEventTeamsId(), playerId).isPresent()) {
                                throw new DomainException("Player " + playerId + " is already added to team "
                                                + command.teamId() + " in event " + command.eventId());
                        }
                }

                for (Integer playerId : playerIds) {
                        eventPlayerRepository.save(new EventPlayer(null, eventTeam.getEventTeamsId(), playerId));
        }
    }
}
