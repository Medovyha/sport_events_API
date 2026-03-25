package com.sport_events.api.application.usecase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.application.dto.command.UpdateTeamCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Sport;
import com.sport_events.api.domain.model.Team;
import com.sport_events.api.domain.repository.SportRepository;
import com.sport_events.api.domain.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class UpdateTeamUseCaseTest {

    @Mock
    private TeamRepository teamRepository;
    @Mock
    private SportRepository sportRepository;

    private UpdateTeamUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateTeamUseCase(teamRepository, sportRepository);
    }

    @Test
    void execute_updatesTeamAndReturnsResult() {
        when(teamRepository.findById(7)).thenReturn(Optional.of(new Team(7, "Old", 1)));
        when(sportRepository.findById(1)).thenReturn(Optional.of(new Sport(1)));
        when(teamRepository.save(any())).thenReturn(new Team(7, "Updated", 1));

        var result = useCase.execute(new UpdateTeamCommand(7, "Updated", 1));

        assertThat(result.teamId()).isEqualTo(7);
        assertThat(result.name()).isEqualTo("Updated");
    }

    @Test
    void execute_throwsWhenTeamNotFound() {
        when(teamRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new UpdateTeamCommand(99, "X", 1)))
                .isInstanceOf(DomainException.class)
                .hasMessage("Team not found: 99");
    }

    @Test
    void execute_throwsWhenSportNotFound() {
        when(teamRepository.findById(7)).thenReturn(Optional.of(new Team(7, "Old", 1)));
        when(sportRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new UpdateTeamCommand(7, "X", 99)))
                .isInstanceOf(DomainException.class)
                .hasMessage("Sport not found: 99");
    }

    @Test
    void deleteById_deletesWhenTeamExists() {
        when(teamRepository.findById(7)).thenReturn(Optional.of(new Team(7, "Old", 1)));

        useCase.deleteById(7);

        verify(teamRepository).deleteById(7);
    }

    @Test
    void deleteById_throwsWhenTeamNotFound() {
        when(teamRepository.findById(77)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.deleteById(77))
                .isInstanceOf(DomainException.class)
                .hasMessage("Team not found: 77");
    }
}
