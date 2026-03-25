package com.sport_events.api.application.usecase;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.application.dto.command.UpdateEventTeamPlayersCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.model.EventPlayer;
import com.sport_events.api.domain.model.EventTeam;
import com.sport_events.api.domain.model.Player;
import com.sport_events.api.domain.model.Team;
import com.sport_events.api.domain.model.TeamPlayer;
import com.sport_events.api.domain.repository.EventPlayerRepository;
import com.sport_events.api.domain.repository.EventRepository;
import com.sport_events.api.domain.repository.EventTeamRepository;
import com.sport_events.api.domain.repository.PlayerRepository;
import com.sport_events.api.domain.repository.TeamPlayerRepository;
import com.sport_events.api.domain.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class UpdateEventTeamPlayersUseCaseTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private TeamPlayerRepository teamPlayerRepository;
    @Mock
    private EventTeamRepository eventTeamRepository;
    @Mock
    private EventPlayerRepository eventPlayerRepository;

    private UpdateEventTeamPlayersUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateEventTeamPlayersUseCase(
                eventRepository,
                teamRepository,
                playerRepository,
                teamPlayerRepository,
                eventTeamRepository,
                eventPlayerRepository);
    }

    @Test
    void execute_replacesAssignments() {
        when(eventRepository.findById(5L)).thenReturn(Optional.of(new Event(5L, null, 10)));
        when(teamRepository.findById(7)).thenReturn(Optional.of(new Team(7, "Real Madrid", 1)));
        when(eventTeamRepository.findByEventIdAndTeamId(5L, 7)).thenReturn(Optional.of(new EventTeam(11, 5L, 7)));
        when(playerRepository.findById(100)).thenReturn(Optional.of(new Player(100, "A", "A", null)));
        when(playerRepository.findById(101)).thenReturn(Optional.of(new Player(101, "B", "B", null)));
        when(teamPlayerRepository.findByTeamIdAndPlayerId(7, 100)).thenReturn(Optional.of(new TeamPlayer(1, 7, 100)));
        when(teamPlayerRepository.findByTeamIdAndPlayerId(7, 101)).thenReturn(Optional.of(new TeamPlayer(2, 7, 101)));
        when(eventPlayerRepository.findByEventTeamId(11)).thenReturn(List.of(
                new EventPlayer(20, 11, 100),
                new EventPlayer(21, 11, 102)));
        when(eventPlayerRepository.save(any())).thenReturn(new EventPlayer(22, 11, 101));

        assertThatCode(() -> useCase.execute(new UpdateEventTeamPlayersCommand(5L, 7, List.of(100, 101))))
                .doesNotThrowAnyException();

        verify(eventPlayerRepository).deleteById(21);
        verify(eventPlayerRepository, times(1)).save(any());
    }

    @Test
    void execute_allowsClearingAssignments() {
        when(eventRepository.findById(5L)).thenReturn(Optional.of(new Event(5L, null, 10)));
        when(teamRepository.findById(7)).thenReturn(Optional.of(new Team(7, "Real Madrid", 1)));
        when(eventTeamRepository.findByEventIdAndTeamId(5L, 7)).thenReturn(Optional.of(new EventTeam(11, 5L, 7)));
        when(eventPlayerRepository.findByEventTeamId(11)).thenReturn(List.of(
                new EventPlayer(20, 11, 100),
                new EventPlayer(21, 11, 101)));

        assertThatCode(() -> useCase.execute(new UpdateEventTeamPlayersCommand(5L, 7, List.of())))
                .doesNotThrowAnyException();

        verify(eventPlayerRepository).deleteById(20);
        verify(eventPlayerRepository).deleteById(21);
        verify(eventPlayerRepository, never()).save(any());
    }

    @Test
    void execute_throwsWhenDuplicatePlayerInRequest() {
        when(eventRepository.findById(5L)).thenReturn(Optional.of(new Event(5L, null, 10)));
        when(teamRepository.findById(7)).thenReturn(Optional.of(new Team(7, "Real Madrid", 1)));
        when(eventTeamRepository.findByEventIdAndTeamId(5L, 7)).thenReturn(Optional.of(new EventTeam(11, 5L, 7)));
        when(playerRepository.findById(100)).thenReturn(Optional.of(new Player(100, "A", "A", null)));
        when(teamPlayerRepository.findByTeamIdAndPlayerId(7, 100)).thenReturn(Optional.of(new TeamPlayer(1, 7, 100)));

        assertThatThrownBy(() -> useCase.execute(new UpdateEventTeamPlayersCommand(5L, 7, List.of(100, 100))))
                .isInstanceOf(DomainException.class)
                .hasMessage("Duplicate player in request: 100");
    }
}
