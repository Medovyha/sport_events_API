package com.sport_events.api.application.usecase;

import com.sport_events.api.application.dto.query.GetEventQuery;
import com.sport_events.api.application.dto.result.EventResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.repository.EventRepository;

public class GetEventUseCase {

    private final EventRepository eventRepository;
    private final EventResultBuilder eventResultBuilder;

    public GetEventUseCase(
            EventRepository eventRepository,
            EventResultBuilder eventResultBuilder) {
        this.eventRepository = eventRepository;
        this.eventResultBuilder = eventResultBuilder;
    }

    public EventResult execute(GetEventQuery query) {
        Event event = eventRepository.findById(query.eventId())
                .orElseThrow(() -> new DomainException("Event not found: " + query.eventId()));

        return eventResultBuilder.build(event, query.languageCode());
    }
}
