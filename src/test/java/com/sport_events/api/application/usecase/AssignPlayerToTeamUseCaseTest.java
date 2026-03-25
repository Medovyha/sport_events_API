package com.sport_events.api.application.usecase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.application.dto.command.AssignPlayerToTeamCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Player;
import com.sport_events.api.domain.model.Team;
import com.sport_events.api.domain.model.TeamPlayer;
import com.sport_events.api.domain.repository.PlayerRepository;
import com.sport_events.api.domain.repository.TeamPlayerRepository;
import com.sport_events.api.domain.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class AssignPlayerToTeamUseCaseTest {

    @Mock
    private TeamPlayerRepository teamPlayerRepository;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private PlayerRepository playerRepository;

    private AssignPlayerToTeamUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new AssignPlayerToTeamUseCase(teamPlayerRepository, teamRepository, playerRepository);
    }

    @Test
    void execute_assignsPlayerSuccessfully() {
        when(teamRepository.findById(7)).thenReturn(Optional.of(new Team(7, "Real Madrid", 1)));
        when(playerRepository.findById(100)).thenReturn(Optional.of(
                new Player(100, "Carlos", "Silva", null)));
        when(teamPlayerRepository.findByTeamIdAndPlayerId(7, 100)).thenReturn(Optional.empty());
        when(teamPlayerRepository.save(any())).thenReturn(new TeamPlayer(1, 7, 100));

        assertThatCode(() -> useCase.execute(new AssignPlayerToTeamCommand(7, 100)))
                .doesNotThrowAnyException();
        verify(teamPlayerRepository).save(any());
    }

    @Test
    void execute_throwsWhenTeamNotFound() {
        when(teamRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new AssignPlayerToTeamCommand(99, 100)))
                .isInstanceOf(DomainException.class)
                .hasMessage("Team not found: 99");
    }

    @Test
    void execute_throwsWhenPlayerNotFound() {
        when(teamRepository.findById(7)).thenReturn(Optional.of(new Team(7, "Real Madrid", 1)));
        when(playerRepository.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new AssignPlayerToTeamCommand(7, 999)))
                .isInstanceOf(DomainException.class)
                .hasMessage("Player not found: 999");
    }

    @Test
    void execute_throwsWhenAlreadyAssigned() {
        when(teamRepository.findById(7)).thenReturn(Optional.of(new Team(7, "Real Madrid", 1)));
        when(playerRepository.findById(100)).thenReturn(Optional.of(
                new Player(100, "Carlos", "Silva", null)));
        when(teamPlayerRepository.findByTeamIdAndPlayerId(7, 100))
                .thenReturn(Optional.of(new TeamPlayer(1, 7, 100)));

        assertThatThrownBy(() -> useCase.execute(new AssignPlayerToTeamCommand(7, 100)))
                .isInstanceOf(DomainException.class)
                .hasMessage("Player 100 is already assigned to team 7");
    }
}
