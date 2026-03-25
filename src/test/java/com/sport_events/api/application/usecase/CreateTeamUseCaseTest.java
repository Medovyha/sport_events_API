package com.sport_events.api.application.usecase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.application.dto.command.CreateTeamCommand;
import com.sport_events.api.application.dto.result.TeamResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Sport;
import com.sport_events.api.domain.model.Team;
import com.sport_events.api.domain.repository.SportRepository;
import com.sport_events.api.domain.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class CreateTeamUseCaseTest {

    @Mock
    private TeamRepository teamRepository;
    @Mock
    private SportRepository sportRepository;

    private CreateTeamUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateTeamUseCase(teamRepository, sportRepository);
    }

    @Test
    void execute_savesTeamAndReturnsResult() {
        when(sportRepository.findById(1)).thenReturn(Optional.of(new Sport(1)));
        Team saved = new Team(10, "Real Madrid", 1);
        when(teamRepository.save(any())).thenReturn(saved);

        TeamResult result = useCase.execute(new CreateTeamCommand("Real Madrid", 1));

        assertThat(result.teamId()).isEqualTo(10);
        assertThat(result.name()).isEqualTo("Real Madrid");
        assertThat(result.sportId()).isEqualTo(1);
        assertThat(result.sportName()).isNull();
    }

    @Test
    void execute_throwsWhenSportNotFound() {
        when(sportRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new CreateTeamCommand("Unknown", 99)))
                .isInstanceOf(DomainException.class)
                .hasMessage("Sport not found: 99");
    }
}
