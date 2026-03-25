package com.sport_events.api.presentation.mapper;

import java.util.List;

import com.sport_events.api.application.dto.result.EventsListResult;
import com.sport_events.api.presentation.dto.EventsSummaryResponse;

public class EventsListMapper {

    public static List<EventsSummaryResponse> toResponse(EventsListResult result) {
        return result.events()
                .stream()
                .map(eventResult -> {
                    EventsSummaryResponse.VenueResponse venueResponse = eventResult.venue() != null
                            ? new EventsSummaryResponse.VenueResponse(
                                    eventResult.venue().venueId(),
                                    eventResult.venue().name(),
                                    eventResult.venue().address())
                            : null;

                    String name = eventResult.translation() != null ? eventResult.translation().name() : null;
                    String description = eventResult.translation() != null ? eventResult.translation().description() : null;

                    return new EventsSummaryResponse(
                            eventResult.eventId(),
                            eventResult.startsAt(),
                            name,
                            description,
                            venueResponse);
                })
                .toList();
    }
}
