package com.sport_events.api.application.dto.command;

public record AssignPlayerToTeamCommand(
        Integer teamId,
        Integer playerId
) {
}
