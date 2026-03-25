package com.sport_events.api.application.dto.command;

public record CreateTeamCommand(
        String name,
        Integer sportId
) {
}
