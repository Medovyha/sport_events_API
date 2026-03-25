package com.sport_events.api.presentation.controller;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.sport_events.api.application.dto.result.PlayerResult;
import com.sport_events.api.application.usecase.GetPlayerUseCase;
import com.sport_events.api.presentation.dto.PlayerResponse;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    private GetPlayerUseCase getPlayerUseCase;

    @InjectMocks
    private PlayerController controller;

    @Test
    void getAllPlayers_returnsOkWithList() {
        when(getPlayerUseCase.findAll("en")).thenReturn(List.of(
                new PlayerResult(1, "Carlos", "Silva", LocalDate.parse("1990-03-15"), null),
                new PlayerResult(2, "Alice", "Smith", LocalDate.parse("1995-06-20"), null)));

        ResponseEntity<List<PlayerResponse>> response = controller.getAllPlayers("en");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).firstName()).isEqualTo("Carlos");
        assertThat(response.getBody().get(1).firstName()).isEqualTo("Alice");
    }

    @Test
    void getPlayer_returnsOkWithPlayerResponse() {
        when(getPlayerUseCase.findById(1, "en")).thenReturn(
                new PlayerResult(1, "Carlos", "Silva", LocalDate.parse("1990-03-15"), null));

        ResponseEntity<PlayerResponse> response = controller.getPlayer(1, "en");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().playerId()).isEqualTo(1);
        assertThat(response.getBody().lastName()).isEqualTo("Silva");
    }
}
