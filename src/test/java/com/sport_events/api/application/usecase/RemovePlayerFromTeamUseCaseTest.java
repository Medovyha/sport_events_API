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

import com.sport_events.api.application.dto.command.RemovePlayerFromTeamCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.TeamPlayer;
import com.sport_events.api.domain.repository.TeamPlayerRepository;

@ExtendWith(MockitoExtension.class)
class RemovePlayerFromTeamUseCaseTest {

    @Mock
    private TeamPlayerRepository teamPlayerRepository;

    private RemovePlayerFromTeamUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new RemovePlayerFromTeamUseCase(teamPlayerRepository);
    }

    @Test
    void execute_removesExistingAssignment() {
        when(teamPlayerRepository.findByTeamIdAndPlayerId(7, 100)).thenReturn(Optional.of(new TeamPlayer(1, 7, 100)));

        assertThatCode(() -> useCase.execute(new RemovePlayerFromTeamCommand(7, 100)))
                .doesNotThrowAnyException();

        verify(teamPlayerRepository).deleteById(1);
    }

    @Test
    void execute_throwsWhenAssignmentMissing() {
        when(teamPlayerRepository.findByTeamIdAndPlayerId(7, 100)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new RemovePlayerFromTeamCommand(7, 100)))
                .isInstanceOf(DomainException.class)
                .hasMessage("Player 100 is not in team 7");
    }
}
