package com.sport_events.api.application.dto.result;

import java.time.OffsetDateTime;
import java.util.List;

public record EventResult(
        Long eventId,
        OffsetDateTime startsAt,
        VenueResult venue,
        List<EventTeamResult> teams,
        EventTranslationResult translation
) {
}
