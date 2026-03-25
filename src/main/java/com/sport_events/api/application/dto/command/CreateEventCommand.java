package com.sport_events.api.application.dto.command;

import java.time.OffsetDateTime;
import java.util.List;

public record CreateEventCommand(
	OffsetDateTime startsAt,
	Integer venueId,
	List<EventTranslationCommand> translations
) {
}
