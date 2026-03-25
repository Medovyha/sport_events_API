package com.sport_events.api.application.dto.command;

import java.time.OffsetDateTime;
import java.util.List;

public record UpdateEventCommand(
        Long eventId,
        OffsetDateTime startsAt,
        Integer venueId,
        List<EventTranslationCommand> translations
) {
}
