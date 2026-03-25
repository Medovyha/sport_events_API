package com.sport_events.api.application.usecase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sport_events.api.application.dto.command.UpdateEventTeamPlayersCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.EventPlayer;
import com.sport_events.api.domain.model.EventTeam;
import com.sport_events.api.domain.repository.EventPlayerRepository;
import com.sport_events.api.domain.repository.EventRepository;
import com.sport_events.api.domain.repository.EventTeamRepository;
import com.sport_events.api.domain.repository.PlayerRepository;
import com.sport_events.api.domain.repository.TeamPlayerRepository;
import com.sport_events.api.domain.repository.TeamRepository;

public class UpdateEventTeamPlayersUseCase {

    private final EventRepository eventRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final TeamPlayerRepository teamPlayerRepository;
    private final EventTeamRepository eventTeamRepository;
    private final EventPlayerRepository eventPlayerRepository;

    public UpdateEventTeamPlayersUseCase(
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

    public void execute(UpdateEventTeamPlayersCommand command) {
        List<Integer> playerIds = command.playerIds();
        if (playerIds == null) {
            throw new DomainException("Player list is required");
        }

        eventRepository.findById(command.eventId())
                .orElseThrow(() -> new DomainException("Event not found: " + command.eventId()));

        teamRepository.findById(command.teamId())
                .orElseThrow(() -> new DomainException("Team not found: " + command.teamId()));

        EventTeam eventTeam = eventTeamRepository.findByEventIdAndTeamId(command.eventId(), command.teamId())
                .orElseThrow(() -> new DomainException(
                        "Team " + command.teamId() + " is not added to event " + command.eventId()));

        Set<Integer> requestedPlayerIds = validateRequestedPlayers(command.teamId(), command.eventId(), playerIds,
                eventTeam.getEventTeamsId());

        List<EventPlayer> existingAssignments = eventPlayerRepository.findByEventTeamId(eventTeam.getEventTeamsId());
        Set<Integer> existingPlayerIds = existingAssignments.stream()
                .map(EventPlayer::getPlayerId)
                .collect(java.util.stream.Collectors.toSet());

        for (EventPlayer existingAssignment : existingAssignments) {
            if (!requestedPlayerIds.contains(existingAssignment.getPlayerId())) {
                eventPlayerRepository.deleteById(existingAssignment.getEventPlayersId());
            }
        }

        for (Integer playerId : requestedPlayerIds) {
            if (!existingPlayerIds.contains(playerId)) {
                eventPlayerRepository.save(new EventPlayer(null, eventTeam.getEventTeamsId(), playerId));
            }
        }
    }

    private Set<Integer> validateRequestedPlayers(Integer teamId, Long eventId, List<Integer> playerIds, Integer eventTeamId) {
        Set<Integer> requestedPlayerIds = new HashSet<>();
        for (Integer playerId : playerIds) {
            if (!requestedPlayerIds.add(playerId)) {
                throw new DomainException("Duplicate player in request: " + playerId);
            }

            playerRepository.findById(playerId)
                    .orElseThrow(() -> new DomainException("Player not found: " + playerId));

            if (teamPlayerRepository.findByTeamIdAndPlayerId(teamId, playerId).isEmpty()) {
                throw new DomainException("Player " + playerId + " is not assigned to team " + teamId);
            }

            eventPlayerRepository.findByEventTeamIdAndPlayerId(eventTeamId, playerId);
        }
        return requestedPlayerIds;
    }
}
