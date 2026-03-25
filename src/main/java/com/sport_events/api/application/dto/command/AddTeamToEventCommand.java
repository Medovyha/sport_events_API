package com.sport_events.api.application.dto.command;

public record AddTeamToEventCommand(
        Long eventId,
        Integer teamId
) {
}
