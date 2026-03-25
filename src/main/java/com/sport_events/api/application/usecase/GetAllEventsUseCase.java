package com.sport_events.api.application.usecase;

import java.util.List;

import com.sport_events.api.application.dto.query.GetAllEventsQuery;
import com.sport_events.api.application.dto.result.EventResult;
import com.sport_events.api.application.dto.result.EventsListResult;
import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.repository.EventRepository;

public class GetAllEventsUseCase {

    private final EventRepository eventRepository;
    private final EventResultBuilder eventResultBuilder;

    public GetAllEventsUseCase(
            EventRepository eventRepository,
            EventResultBuilder eventResultBuilder) {
        this.eventRepository = eventRepository;
        this.eventResultBuilder = eventResultBuilder;
    }

    public EventsListResult execute(GetAllEventsQuery query) {
        List<Event> events = eventRepository.findAll();
        List<EventResult> results = events.stream()
                .map(event -> eventResultBuilder.build(event, query.languageCode()))
                .toList();
        return new EventsListResult(results);
    }
}
