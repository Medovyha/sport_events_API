package com.sport_events.api.application.dto.query;

public record GetPlayersByTeamQuery(
        Integer teamId,
        String languageCode
) {
}
