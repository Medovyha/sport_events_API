package com.sport_events.api.application.dto.command;

public record RemovePlayerFromEventTeamCommand(
        Long eventId,
        Integer teamId,
        Integer playerId
) {
}
