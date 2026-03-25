package com.sport_events.api.application.usecase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.application.dto.command.RemovePlayerFromEventTeamCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.model.EventPlayer;
import com.sport_events.api.domain.model.EventTeam;
import com.sport_events.api.domain.model.Team;
import com.sport_events.api.domain.repository.EventPlayerRepository;
import com.sport_events.api.domain.repository.EventRepository;
import com.sport_events.api.domain.repository.EventTeamRepository;
import com.sport_events.api.domain.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class RemovePlayerFromEventTeamUseCaseTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private EventTeamRepository eventTeamRepository;
    @Mock
    private EventPlayerRepository eventPlayerRepository;

    private RemovePlayerFromEventTeamUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new RemovePlayerFromEventTeamUseCase(
                eventRepository,
                teamRepository,
                eventTeamRepository,
                eventPlayerRepository);
    }

    @Test
    void execute_removesExistingAssignment() {
        when(eventRepository.findById(5L)).thenReturn(Optional.of(new Event(5L, null, 10)));
        when(teamRepository.findById(7)).thenReturn(Optional.of(new Team(7, "Real Madrid", 1)));
        when(eventTeamRepository.findByEventIdAndTeamId(5L, 7)).thenReturn(Optional.of(new EventTeam(11, 5L, 7)));
        when(eventPlayerRepository.findByEventTeamIdAndPlayerId(11, 100)).thenReturn(Optional.of(new EventPlayer(20, 11, 100)));

        assertThatCode(() -> useCase.execute(new RemovePlayerFromEventTeamCommand(5L, 7, 100)))
                .doesNotThrowAnyException();

        verify(eventPlayerRepository).deleteById(20);
    }

    @Test
    void execute_throwsWhenAssignmentMissing() {
        when(eventRepository.findById(5L)).thenReturn(Optional.of(new Event(5L, null, 10)));
        when(teamRepository.findById(7)).thenReturn(Optional.of(new Team(7, "Real Madrid", 1)));
        when(eventTeamRepository.findByEventIdAndTeamId(5L, 7)).thenReturn(Optional.of(new EventTeam(11, 5L, 7)));
        when(eventPlayerRepository.findByEventTeamIdAndPlayerId(11, 100)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new RemovePlayerFromEventTeamCommand(5L, 7, 100)))
                .isInstanceOf(DomainException.class)
                .hasMessage("Player 100 is not in team 7 for event 5");
    }
}
