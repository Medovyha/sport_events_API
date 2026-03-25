package com.sport_events.api.events;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.sport_events.api.application.dto.command.RemoveTeamFromEventCommand;
import com.sport_events.api.application.usecase.RemoveTeamFromEventUseCase;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.model.EventTeam;
import com.sport_events.api.domain.model.Team;
import com.sport_events.api.domain.repository.EventRepository;
import com.sport_events.api.domain.repository.EventTeamRepository;
import com.sport_events.api.domain.repository.TeamRepository;

class RemoveTeamFromEventUseCaseTest {

    private RemoveTeamFromEventUseCase useCase;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private EventTeamRepository eventTeamRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new RemoveTeamFromEventUseCase(eventRepository, teamRepository, eventTeamRepository);
    }

    @Test
    void testRemoveTeamFromEventSuccess() {
        Long eventId = 1L;
        Integer teamId = 10;
        Integer eventTeamId = 100;

        Event event = new Event(eventId, OffsetDateTime.now().plusDays(1), 1);
        Team team = new Team(teamId, "Test Team", 1);
        EventTeam eventTeam = new EventTeam(eventTeamId, eventId, teamId);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(eventTeamRepository.findByEventIdAndTeamId(eventId, teamId)).thenReturn(Optional.of(eventTeam));
        doNothing().when(eventTeamRepository).deleteById(eventTeamId);

        RemoveTeamFromEventCommand command = new RemoveTeamFromEventCommand(eventId, teamId);
        useCase.execute(command);

        verify(eventRepository).findById(eventId);
        verify(teamRepository).findById(teamId);
        verify(eventTeamRepository).findByEventIdAndTeamId(eventId, teamId);
        verify(eventTeamRepository).deleteById(eventTeamId);
    }

    @Test
    void testRemoveTeamFromEventNotFound() {
        Long eventId = 1L;
        Integer teamId = 10;

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        RemoveTeamFromEventCommand command = new RemoveTeamFromEventCommand(eventId, teamId);
        DomainException exception = assertThrows(DomainException.class, () -> useCase.execute(command));

        assertEquals("Event not found: 1", exception.getMessage());
        verify(eventTeamRepository, never()).deleteById(any());
    }

    @Test
    void testRemoveTeamNotFound() {
        Long eventId = 1L;
        Integer teamId = 10;
        Event event = new Event(eventId, OffsetDateTime.now().plusDays(1), 1);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        RemoveTeamFromEventCommand command = new RemoveTeamFromEventCommand(eventId, teamId);
        DomainException exception = assertThrows(DomainException.class, () -> useCase.execute(command));

        assertEquals("Team not found: 10", exception.getMessage());
        verify(eventTeamRepository, never()).deleteById(any());
    }

    @Test
    void testTeamNotInEvent() {
        Long eventId = 1L;
        Integer teamId = 10;
        Event event = new Event(eventId, OffsetDateTime.now().plusDays(1), 1);
        Team team = new Team(teamId, "Test Team", 1);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(eventTeamRepository.findByEventIdAndTeamId(eventId, teamId)).thenReturn(Optional.empty());

        RemoveTeamFromEventCommand command = new RemoveTeamFromEventCommand(eventId, teamId);
        DomainException exception = assertThrows(DomainException.class, () -> useCase.execute(command));

        assertEquals("Team 10 is not in event 1", exception.getMessage());
        verify(eventTeamRepository, never()).deleteById(any());
    }
}
