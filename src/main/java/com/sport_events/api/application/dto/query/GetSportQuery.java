package com.sport_events.api.application.dto.query;

public record GetSportQuery(
        Integer sportId,
        String languageCode
) {
}
