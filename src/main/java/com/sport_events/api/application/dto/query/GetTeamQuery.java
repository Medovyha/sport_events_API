package com.sport_events.api.application.dto.query;

public record GetTeamQuery(
        Integer teamId,
        String languageCode
) {
}
