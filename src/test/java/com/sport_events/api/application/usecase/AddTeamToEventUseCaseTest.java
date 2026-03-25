package com.sport_events.api.application.usecase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.application.dto.command.AddTeamToEventCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.model.EventTeam;
import com.sport_events.api.domain.model.Team;
import com.sport_events.api.domain.repository.EventRepository;
import com.sport_events.api.domain.repository.EventTeamRepository;
import com.sport_events.api.domain.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class AddTeamToEventUseCaseTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private EventTeamRepository eventTeamRepository;

    private AddTeamToEventUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new AddTeamToEventUseCase(eventRepository, teamRepository, eventTeamRepository);
    }

    @Test
    void execute_addsTeamToEvent() {
        when(eventRepository.findById(5L)).thenReturn(Optional.of(new Event(5L, null, 10)));
        when(teamRepository.findById(7)).thenReturn(Optional.of(new Team(7, "Real Madrid", 1)));
        when(eventTeamRepository.findByEventIdAndTeamId(5L, 7)).thenReturn(Optional.empty());
        when(eventTeamRepository.save(any())).thenReturn(new EventTeam(11, 5L, 7));

        assertThatCode(() -> useCase.execute(new AddTeamToEventCommand(5L, 7)))
                .doesNotThrowAnyException();

        verify(eventTeamRepository).save(any());
    }

    @Test
    void execute_throwsWhenEventMissing() {
        when(eventRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new AddTeamToEventCommand(5L, 7)))
                .isInstanceOf(DomainException.class)
                .hasMessage("Event not found: 5");
    }

    @Test
    void execute_throwsWhenAlreadyAdded() {
        when(eventRepository.findById(5L)).thenReturn(Optional.of(new Event(5L, null, 10)));
        when(teamRepository.findById(7)).thenReturn(Optional.of(new Team(7, "Real Madrid", 1)));
        when(eventTeamRepository.findByEventIdAndTeamId(5L, 7)).thenReturn(Optional.of(new EventTeam(11, 5L, 7)));

        assertThatThrownBy(() -> useCase.execute(new AddTeamToEventCommand(5L, 7)))
                .isInstanceOf(DomainException.class)
                .hasMessage("Team 7 is already added to event 5");
    }
}
