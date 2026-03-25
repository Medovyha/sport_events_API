package com.sport_events.api.events;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.sport_events.api.application.dto.command.DeleteEventCommand;
import com.sport_events.api.application.usecase.DeleteEventUseCase;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.repository.EventRepository;

class DeleteEventUseCaseTest {

    private DeleteEventUseCase useCase;

    @Mock
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new DeleteEventUseCase(eventRepository);
    }

    @Test
    void testDeleteEventSuccess() {
        Long eventId = 1L;
        Event event = new Event(eventId, OffsetDateTime.now().plusDays(1), 1);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        doNothing().when(eventRepository).deleteById(eventId);

        DeleteEventCommand command = new DeleteEventCommand(eventId);
        useCase.execute(command);

        verify(eventRepository).findById(eventId);
        verify(eventRepository).deleteById(eventId);
    }

    @Test
    void testDeleteEventNotFound() {
        Long eventId = 1L;

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        DeleteEventCommand command = new DeleteEventCommand(eventId);
        DomainException exception = assertThrows(DomainException.class, () -> useCase.execute(command));

        assertEquals("Event not found: 1", exception.getMessage());
        verify(eventRepository, never()).deleteById(eventId);
    }
}
