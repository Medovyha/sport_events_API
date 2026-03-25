package com.sport_events.api.application.usecase;

import java.time.OffsetDateTime;
import java.util.List;

import com.sport_events.api.application.dto.query.GetPreviousEventsQuery;
import com.sport_events.api.application.dto.result.EventResult;
import com.sport_events.api.application.dto.result.EventsListResult;
import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.repository.EventRepository;

public class GetPreviousEventsUseCase {

    private final EventRepository eventRepository;
    private final EventResultBuilder eventResultBuilder;

    public GetPreviousEventsUseCase(
            EventRepository eventRepository,
            EventResultBuilder eventResultBuilder) {
        this.eventRepository = eventRepository;
        this.eventResultBuilder = eventResultBuilder;
    }

    public EventsListResult execute(GetPreviousEventsQuery query) {
        OffsetDateTime now = OffsetDateTime.now();
        List<Event> events = eventRepository.findAll()
                .stream()
                .filter(event -> event.getStartsAt().isBefore(now))
                .toList();
        List<EventResult> results = events.stream()
                .map(event -> eventResultBuilder.build(event, query.languageCode()))
                .toList();
        return new EventsListResult(results);
    }
}
