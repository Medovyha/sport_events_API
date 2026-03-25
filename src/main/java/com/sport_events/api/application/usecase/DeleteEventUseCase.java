package com.sport_events.api.application.usecase;

import com.sport_events.api.application.dto.command.DeleteEventCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.repository.EventRepository;

public class DeleteEventUseCase {

    private final EventRepository eventRepository;

    public DeleteEventUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void execute(DeleteEventCommand command) {
        eventRepository.findById(command.eventId())
                .orElseThrow(() -> new DomainException("Event not found: " + command.eventId()));

        eventRepository.deleteById(command.eventId());
    }
}
