package com.sport_events.api.application.dto.query;

public record GetPlayerQuery(
        Integer playerId,
        String languageCode
) {
}
