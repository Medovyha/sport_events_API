package com.sport_events.api.application.usecase;

import java.time.LocalDate;
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

import com.sport_events.api.application.dto.command.UpdatePlayerCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Player;
import com.sport_events.api.domain.repository.PlayerRepository;

@ExtendWith(MockitoExtension.class)
class UpdatePlayerUseCaseTest {

    @Mock
    private PlayerRepository playerRepository;

    private UpdatePlayerUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdatePlayerUseCase(playerRepository);
    }

    @Test
    void execute_updatesPlayerAndReturnsResult() {
        when(playerRepository.findById(1)).thenReturn(Optional.of(new Player(1, "Old", "Name", LocalDate.parse("1990-01-01"))));
        when(playerRepository.save(any())).thenReturn(new Player(1, "New", "Name", LocalDate.parse("1990-01-01")));

        var result = useCase.execute(new UpdatePlayerCommand(1, "New", "Name", LocalDate.parse("1990-01-01")));

        assertThat(result.playerId()).isEqualTo(1);
        assertThat(result.firstName()).isEqualTo("New");
    }

    @Test
    void execute_throwsWhenPlayerNotFound() {
        when(playerRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new UpdatePlayerCommand(99, "A", "B", LocalDate.parse("1990-01-01"))))
                .isInstanceOf(DomainException.class)
                .hasMessage("Player not found: 99");
    }

    @Test
    void deleteById_deletesWhenPlayerExists() {
        when(playerRepository.findById(1)).thenReturn(Optional.of(new Player(1, "A", "B", LocalDate.parse("1990-01-01"))));

        useCase.deleteById(1);

        verify(playerRepository).deleteById(1);
    }

    @Test
    void deleteById_throwsWhenPlayerNotFound() {
        when(playerRepository.findById(44)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.deleteById(44))
                .isInstanceOf(DomainException.class)
                .hasMessage("Player not found: 44");
    }
}
