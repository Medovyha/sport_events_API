package com.sport_events.api.application.dto.command;

import java.util.List;

public record AddPlayerToEventTeamCommand(
        Long eventId,
        Integer teamId,
        List<Integer> playerIds
) {
}
