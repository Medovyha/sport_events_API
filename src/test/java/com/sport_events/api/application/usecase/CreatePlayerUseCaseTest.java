package com.sport_events.api.application.usecase;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.application.dto.command.CreatePlayerCommand;
import com.sport_events.api.application.dto.result.PlayerResult;
import com.sport_events.api.domain.model.Player;
import com.sport_events.api.domain.repository.PlayerRepository;

@ExtendWith(MockitoExtension.class)
class CreatePlayerUseCaseTest {

    @Mock
    private PlayerRepository playerRepository;

    private CreatePlayerUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreatePlayerUseCase(playerRepository);
    }

    @Test
    void execute_savesPlayerAndReturnsResult() {
        CreatePlayerCommand command = new CreatePlayerCommand("Carlos", "Silva", LocalDate.parse("1990-03-15"));
        Player saved = new Player(42, "Carlos", "Silva", LocalDate.parse("1990-03-15"));
        when(playerRepository.save(any())).thenReturn(saved);

        PlayerResult result = useCase.execute(command);

        assertThat(result.playerId()).isEqualTo(42);
        assertThat(result.firstName()).isEqualTo("Carlos");
        assertThat(result.lastName()).isEqualTo("Silva");
        assertThat(result.dateOfBirth()).isEqualTo(LocalDate.parse("1990-03-15"));
        assertThat(result.teams()).isNull();
    }
}
