package com.sport_events.api.presentation.controller;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.sport_events.api.application.dto.result.SportResult;
import com.sport_events.api.application.dto.result.TeamResult;
import com.sport_events.api.application.usecase.GetSportUseCase;
import com.sport_events.api.application.usecase.GetTeamUseCase;
import com.sport_events.api.presentation.dto.SportResponse;
import com.sport_events.api.presentation.dto.TeamResponse;

@ExtendWith(MockitoExtension.class)
class SportControllerTest {

    @Mock
    private GetSportUseCase getSportUseCase;
    @Mock
    private GetTeamUseCase getTeamUseCase;

    @InjectMocks
    private SportController controller;

    @Test
    void getAllSports_normalizesAcceptLanguage() {
        when(getSportUseCase.findAll("pl")).thenReturn(List.of(
                new SportResult(1, "Piłka nożna"),
                new SportResult(2, "Koszykówka")));

        ResponseEntity<List<SportResponse>> response = controller.getAllSports("pl-PL,pl;q=0.9");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(getSportUseCase).findAll(captor.capture());
        assertThat(captor.getValue()).isEqualTo("pl");
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).name()).isEqualTo("Piłka nożna");
    }

    @Test
    void getSport_normalizesAcceptLanguageAndReturnsSport() {
        when(getSportUseCase.findById(1, "en")).thenReturn(new SportResult(1, "Football"));

        ResponseEntity<SportResponse> response = controller.getSport(1, "en");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().sportId()).isEqualTo(1);
        assertThat(response.getBody().name()).isEqualTo("Football");
    }

    @Test
    void getTeamsBySport_returnsTeamsForThatSport() {
        when(getTeamUseCase.findBySportId(1, "en")).thenReturn(List.of(
                new TeamResult(7, "Real Madrid", 1, "Football"),
                new TeamResult(8, "Barcelona", 1, "Football")));

        ResponseEntity<List<TeamResponse>> response = controller.getTeamsBySport(1, "en");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).name()).isEqualTo("Real Madrid");
        assertThat(response.getBody().get(1).name()).isEqualTo("Barcelona");
    }
}
