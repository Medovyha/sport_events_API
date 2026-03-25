package com.sport_events.api.application.dto.command;

public record RemovePlayerFromTeamCommand(
        Integer teamId,
        Integer playerId
) {
}
