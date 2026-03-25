package com.sport_events.api.presentation.controller;

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

import com.sport_events.api.application.dto.result.SportResult;
import com.sport_events.api.application.dto.result.TeamResult;
import com.sport_events.api.application.dto.query.GetSportQuery;
import com.sport_events.api.application.dto.query.GetSportsQuery;
import com.sport_events.api.application.dto.query.GetTeamsBySportQuery;
import com.sport_events.api.application.usecase.CreateSportUseCase;
import com.sport_events.api.application.usecase.GetSportUseCase;
import com.sport_events.api.application.usecase.GetTeamUseCase;
import com.sport_events.api.application.usecase.UpdateSportUseCase;
import com.sport_events.api.application.usecase.UpsertSportTranslationUseCase;
import com.sport_events.api.presentation.dto.CreateSportRequest;
import com.sport_events.api.presentation.dto.SportResponse;
import com.sport_events.api.presentation.dto.SportTranslationRequest;
import com.sport_events.api.presentation.dto.TeamResponse;
import com.sport_events.api.presentation.dto.UpdateSportRequest;
import com.sport_events.api.presentation.dto.UpsertSportTranslationRequest;

@ExtendWith(MockitoExtension.class)
class SportControllerTest {

    @Mock
    private GetSportUseCase getSportUseCase;
    @Mock
    private GetTeamUseCase getTeamUseCase;
    @Mock
    private CreateSportUseCase createSportUseCase;
    @Mock
    private UpdateSportUseCase updateSportUseCase;
    @Mock
    private UpsertSportTranslationUseCase upsertSportTranslationUseCase;

    @InjectMocks
    private SportController controller;

    @Test
    void getAllSports_normalizesAcceptLanguage() {
        when(getSportUseCase.execute(any(GetSportsQuery.class))).thenReturn(List.of(
                new SportResult(1, "Piłka nożna"),
                new SportResult(2, "Koszykówka")));

        ResponseEntity<List<SportResponse>> response = controller.getAllSports("pl-PL,pl;q=0.9");

        ArgumentCaptor<GetSportsQuery> captor = ArgumentCaptor.forClass(GetSportsQuery.class);
        verify(getSportUseCase).execute(captor.capture());
        assertThat(captor.getValue().languageCode()).isEqualTo("pl");
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).name()).isEqualTo("Piłka nożna");
    }

    @Test
    void getSport_normalizesAcceptLanguageAndReturnsSport() {
        when(getSportUseCase.execute(any(GetSportQuery.class))).thenReturn(new SportResult(1, "Football"));

        ResponseEntity<SportResponse> response = controller.getSport(1, "en");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().sportId()).isEqualTo(1);
        assertThat(response.getBody().name()).isEqualTo("Football");
    }

    @Test
    void getTeamsBySport_returnsTeamsForThatSport() {
        when(getTeamUseCase.execute(any(GetTeamsBySportQuery.class))).thenReturn(List.of(
                new TeamResult(7, "Real Madrid", 1, "Football"),
                new TeamResult(8, "Barcelona", 1, "Football")));

        ResponseEntity<List<TeamResponse>> response = controller.getTeamsBySport(1, "en");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).name()).isEqualTo("Real Madrid");
        assertThat(response.getBody().get(1).name()).isEqualTo("Barcelona");
    }

    @Test
    void createSport_returns201WithCreatedSport() {
        when(createSportUseCase.execute(any())).thenReturn(new SportResult(5, "Football"));

        ResponseEntity<SportResponse> response = controller.createSport(
            new CreateSportRequest("Football", null));

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().sportId()).isEqualTo(5);
        assertThat(response.getBody().name()).isEqualTo("Football");
    }

    @Test
    void createSport_withTranslations_returns201() {
        when(createSportUseCase.execute(any())).thenReturn(new SportResult(6, "Футбол"));

        ResponseEntity<SportResponse> response = controller.createSport(
            new CreateSportRequest("Football", java.util.List.of(
                new SportTranslationRequest("uk", "Футбол"))));

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().sportId()).isEqualTo(6);
    }

    @Test
    void updateSport_returns200() {
        when(updateSportUseCase.execute(any())).thenReturn(new SportResult(5, "Football"));

        ResponseEntity<SportResponse> response = controller.updateSport(
                5,
            new UpdateSportRequest("Football", null));

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().sportId()).isEqualTo(5);
    }

    @Test
    void upsertTranslation_returns200() {
        when(upsertSportTranslationUseCase.execute(any())).thenReturn(new SportResult(5, "Football"));

        ResponseEntity<SportResponse> response = controller.upsertTranslation(
                5,
                "en",
                new UpsertSportTranslationRequest("Football"));

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().sportId()).isEqualTo(5);
    }
}
