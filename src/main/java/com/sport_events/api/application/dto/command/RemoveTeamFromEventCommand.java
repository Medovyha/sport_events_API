package com.sport_events.api.application.dto.command;

public record RemoveTeamFromEventCommand(
        Long eventId,
        Integer teamId
) {
}
