package com.sport_events.api.presentation.controller;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.sport_events.api.application.dto.result.PlayerResult;
import com.sport_events.api.application.dto.result.TeamResult;
import com.sport_events.api.application.usecase.AssignPlayerToTeamUseCase;
import com.sport_events.api.application.usecase.CreateTeamUseCase;
import com.sport_events.api.application.usecase.GetPlayerUseCase;
import com.sport_events.api.application.usecase.GetTeamUseCase;
import com.sport_events.api.application.usecase.RemovePlayerFromTeamUseCase;
import com.sport_events.api.application.usecase.UpdateTeamUseCase;
import com.sport_events.api.presentation.dto.AssignPlayerRequest;
import com.sport_events.api.presentation.dto.CreateTeamRequest;
import com.sport_events.api.presentation.dto.PlayerResponse;
import com.sport_events.api.presentation.dto.TeamResponse;
import com.sport_events.api.presentation.dto.UpdateTeamRequest;

@ExtendWith(MockitoExtension.class)
class TeamControllerTest {

    @Mock
    private GetTeamUseCase getTeamUseCase;
    @Mock
    private GetPlayerUseCase getPlayerUseCase;
    @Mock
    private CreateTeamUseCase createTeamUseCase;
    @Mock
    private AssignPlayerToTeamUseCase assignPlayerToTeamUseCase;
    @Mock
    private UpdateTeamUseCase updateTeamUseCase;
    @Mock
    private RemovePlayerFromTeamUseCase removePlayerFromTeamUseCase;

    @InjectMocks
    private TeamController controller;

    @Test
    void getAllTeams_normalizesAcceptLanguage() {
        when(getTeamUseCase.findAll("uk")).thenReturn(List.of(
                new TeamResult(7, "Real Madrid", 1, "Футбол")));

        ResponseEntity<List<TeamResponse>> response = controller.getAllTeams("uk-UA,uk;q=0.9");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(getTeamUseCase).findAll(captor.capture());
        assertThat(captor.getValue()).isEqualTo("uk");
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().getFirst().name()).isEqualTo("Real Madrid");
    }

    @Test
    void getTeam_normalizesAcceptLanguageAndReturnsTeam() {
        when(getTeamUseCase.findById(7, "en")).thenReturn(
                new TeamResult(7, "Real Madrid", 1, "Football"));

        ResponseEntity<TeamResponse> response = controller.getTeam(7, "en");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().teamId()).isEqualTo(7);
        assertThat(response.getBody().sportName()).isEqualTo("Football");
    }

    @Test
    void getPlayersByTeam_returnsPlayers() {
        when(getPlayerUseCase.findByTeamId(7, "en")).thenReturn(List.of(
                new PlayerResult(100, "Carlos", "Silva", LocalDate.parse("1990-03-15"), null)));

        ResponseEntity<List<PlayerResponse>> response = controller.getPlayersByTeam(7, "en");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().getFirst().firstName()).isEqualTo("Carlos");
    }

    @Test
    void createTeam_returns201WithCreatedTeam() {
        when(createTeamUseCase.execute(any())).thenReturn(new TeamResult(10, "Real Madrid", 1, null));

        ResponseEntity<TeamResponse> response = controller.createTeam(new CreateTeamRequest("Real Madrid", 1));

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().teamId()).isEqualTo(10);
        assertThat(response.getBody().name()).isEqualTo("Real Madrid");
    }

    @Test
    void assignPlayer_returns204() {
        ResponseEntity<Void> response = controller.assignPlayer(7, new AssignPlayerRequest(100));

        assertThat(response.getStatusCode().value()).isEqualTo(204);
        verify(assignPlayerToTeamUseCase).execute(any());
    }

    @Test
    void updateTeam_returns200WithUpdatedTeam() {
        when(updateTeamUseCase.execute(any())).thenReturn(new TeamResult(7, "Updated Team", 1, null));

        ResponseEntity<TeamResponse> response = controller.updateTeam(7, new UpdateTeamRequest("Updated Team", 1));

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().teamId()).isEqualTo(7);
        assertThat(response.getBody().name()).isEqualTo("Updated Team");
    }

    @Test
    void deleteTeam_returns204() {
        ResponseEntity<Void> response = controller.deleteTeam(7);

        assertThat(response.getStatusCode().value()).isEqualTo(204);
        verify(updateTeamUseCase).deleteById(7);
    }

    @Test
    void removePlayer_returns204() {
        ResponseEntity<Void> response = controller.removePlayer(7, 100);

        assertThat(response.getStatusCode().value()).isEqualTo(204);
        verify(removePlayerFromTeamUseCase).execute(any());
    }
}
