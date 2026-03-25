package com.sport_events.api.application.dto.result;

public record TeamResult(
        Integer teamId,
        String name,
        Integer sportId,
        String sportName
) {
}
