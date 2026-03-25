package com.sport_events.api.application.dto.result;

import java.util.List;

public record EventsListResult(
        List<EventResult> events
) {
}
