package com.sport_events.api.presentation.controller;

import java.time.OffsetDateTime;
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

import com.sport_events.api.application.dto.query.GetEventQuery;
import com.sport_events.api.application.dto.result.EventResult;
import com.sport_events.api.application.dto.result.EventTranslationResult;
import com.sport_events.api.application.dto.result.EventsListResult;
import com.sport_events.api.application.dto.result.VenueResult;
import com.sport_events.api.application.usecase.AddPlayerToEventTeamUseCase;
import com.sport_events.api.application.usecase.AddTeamToEventUseCase;
import com.sport_events.api.application.usecase.CreateEventUseCase;
import com.sport_events.api.application.usecase.DeleteEventUseCase;
import com.sport_events.api.application.usecase.GetAllEventsUseCase;
import com.sport_events.api.application.usecase.GetEventUseCase;
import com.sport_events.api.application.usecase.GetFutureEventsUseCase;
import com.sport_events.api.application.usecase.GetPreviousEventsUseCase;
import com.sport_events.api.application.usecase.RemovePlayerFromEventTeamUseCase;
import com.sport_events.api.application.usecase.RemoveTeamFromEventUseCase;
import com.sport_events.api.application.usecase.UpdateEventTeamPlayersUseCase;
import com.sport_events.api.application.usecase.UpdateEventUseCase;
import com.sport_events.api.presentation.dto.AddPlayerToEventTeamRequest;
import com.sport_events.api.presentation.dto.AddTeamToEventRequest;
import com.sport_events.api.presentation.dto.CreateEventRequest;
import com.sport_events.api.presentation.dto.EventDetailsResponse;
import com.sport_events.api.presentation.dto.EventTranslationRequest;
import com.sport_events.api.presentation.dto.EventsSummaryResponse;
import com.sport_events.api.presentation.dto.UpdateEventRequest;
import com.sport_events.api.presentation.dto.UpdateEventTeamPlayersRequest;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    @Mock
    private GetEventUseCase getEventUseCase;
    @Mock
    private GetAllEventsUseCase getAllEventsUseCase;
    @Mock
    private GetPreviousEventsUseCase getPreviousEventsUseCase;
    @Mock
    private GetFutureEventsUseCase getFutureEventsUseCase;
    @Mock
    private CreateEventUseCase createEventUseCase;
    @Mock
    private UpdateEventUseCase updateEventUseCase;
    @Mock
    private AddTeamToEventUseCase addTeamToEventUseCase;
    @Mock
    private AddPlayerToEventTeamUseCase addPlayerToEventTeamUseCase;
    @Mock
    private UpdateEventTeamPlayersUseCase updateEventTeamPlayersUseCase;
    @Mock
    private RemovePlayerFromEventTeamUseCase removePlayerFromEventTeamUseCase;
    @Mock
    private DeleteEventUseCase deleteEventUseCase;
    @Mock
    private RemoveTeamFromEventUseCase removeTeamFromEventUseCase;

    @InjectMocks
    private EventController controller;

    @Test
    void getEvent_normalizesAcceptLanguageBeforeCallingUseCase() {
        EventResult result = new EventResult(
                1L,
                OffsetDateTime.parse("2026-04-05T20:00:00Z"),
                new VenueResult(1, "Santiago Bernabéu", "Madrid"),
                List.of(),
                new EventTranslationResult(2, 2, "Ель Класіко", "Ukrainian description"));
        when(getEventUseCase.execute(any(GetEventQuery.class))).thenReturn(result);

        ResponseEntity<EventDetailsResponse> response = controller.getEvent(1L, "uk-UA,uk;q=0.9,en;q=0.8");

        ArgumentCaptor<GetEventQuery> captor = ArgumentCaptor.forClass(GetEventQuery.class);
        verify(getEventUseCase).execute(captor.capture());
        assertThat(captor.getValue().eventId()).isEqualTo(1L);
        assertThat(captor.getValue().languageCode()).isEqualTo("uk");
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().name()).isEqualTo("Ель Класіко");
    }

    @Test
    void getAllEvents_returns200WithEventsList() {
        EventResult event = new EventResult(
                1L,
                OffsetDateTime.parse("2026-04-05T20:00:00Z"),
                new VenueResult(1, "Bernabeu", "Madrid"),
                List.of(),
                new EventTranslationResult(11, 1, "El Clasico", "Desc"));
        when(getAllEventsUseCase.execute(any())).thenReturn(new EventsListResult(List.of(event)));

        ResponseEntity<List<EventsSummaryResponse>> response = controller.getAllEvents("en");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        verify(getAllEventsUseCase).execute(any());
    }

    @Test
    void getPreviousEvents_returns200WithEventsList() {
        EventResult event = new EventResult(
                1L,
                OffsetDateTime.parse("2026-04-05T20:00:00Z"),
                new VenueResult(1, "Bernabeu", "Madrid"),
                List.of(),
                new EventTranslationResult(11, 1, "El Clasico", "Desc"));
        when(getPreviousEventsUseCase.execute(any())).thenReturn(new EventsListResult(List.of(event)));

        ResponseEntity<List<EventsSummaryResponse>> response = controller.getPreviousEvents("en");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        verify(getPreviousEventsUseCase).execute(any());
    }

    @Test
    void getFutureEvents_returns200WithEventsList() {
        EventResult event = new EventResult(
                1L,
                OffsetDateTime.parse("2026-04-05T20:00:00Z"),
                new VenueResult(1, "Bernabeu", "Madrid"),
                List.of(),
                new EventTranslationResult(11, 1, "El Clasico", "Desc"));
        when(getFutureEventsUseCase.execute(any())).thenReturn(new EventsListResult(List.of(event)));

        ResponseEntity<List<EventsSummaryResponse>> response = controller.getFutureEvents("en");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        verify(getFutureEventsUseCase).execute(any());
    }

    @Test
    void createEvent_returns201WithCreatedEvent() {
        EventResult result = new EventResult(
                5L,
                OffsetDateTime.parse("2026-04-05T20:00:00Z"),
                new VenueResult(10, "Bernabeu", "Madrid"),
                List.of(),
                new EventTranslationResult(11, 1, "El Clasico", "Desc"));
        when(createEventUseCase.execute(any())).thenReturn(result);

        ResponseEntity<EventDetailsResponse> response = controller.createEvent(new CreateEventRequest(
                OffsetDateTime.parse("2026-04-05T20:00:00Z"),
                10,
                "El Clasico",
                "Desc",
                List.of(new EventTranslationRequest("uk", "Ель Класіко", "Опис"))));

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().eventId()).isEqualTo(5L);
        assertThat(response.getBody().name()).isEqualTo("El Clasico");
    }

    @Test
    void updateEvent_returns200WithUpdatedEvent() {
        EventResult result = new EventResult(
                5L,
                OffsetDateTime.parse("2026-04-05T20:00:00Z"),
                new VenueResult(10, "Bernabeu", "Madrid"),
                List.of(),
                new EventTranslationResult(11, 1, "El Clasico Updated", "Desc"));
        when(updateEventUseCase.execute(any())).thenReturn(result);

        ResponseEntity<EventDetailsResponse> response = controller.updateEvent(5L, new UpdateEventRequest(
                OffsetDateTime.parse("2026-04-05T20:00:00Z"),
                10,
                "El Clasico Updated",
                "Desc",
                List.of()));

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().eventId()).isEqualTo(5L);
        assertThat(response.getBody().name()).isEqualTo("El Clasico Updated");
    }

        @Test
        void addTeamToEvent_returns204() {
                ResponseEntity<Void> response = controller.addTeamToEvent(5L, new AddTeamToEventRequest(7));

                assertThat(response.getStatusCode().value()).isEqualTo(204);
                verify(addTeamToEventUseCase).execute(any());
        }

        @Test
        void addPlayerToEventTeam_returns204() {
                ResponseEntity<Void> response = controller.addPlayerToEventTeam(5L, 7, new AddPlayerToEventTeamRequest(List.of(100, 101)));

                assertThat(response.getStatusCode().value()).isEqualTo(204);
                verify(addPlayerToEventTeamUseCase).execute(any());
        }

        @Test
        void updateEventTeamPlayers_returns204() {
                ResponseEntity<Void> response = controller.updateEventTeamPlayers(5L, 7,
                                new UpdateEventTeamPlayersRequest(List.of(100, 101)));

                assertThat(response.getStatusCode().value()).isEqualTo(204);
                verify(updateEventTeamPlayersUseCase).execute(any());
        }

        @Test
        void removePlayerFromEventTeam_returns204() {
                ResponseEntity<Void> response = controller.removePlayerFromEventTeam(5L, 7, 100);

                assertThat(response.getStatusCode().value()).isEqualTo(204);
                verify(removePlayerFromEventTeamUseCase).execute(any());
        }

        @Test
        void removeTeamFromEvent_returns204() {
                ResponseEntity<Void> response = controller.removeTeamFromEvent(5L, 7);

                assertThat(response.getStatusCode().value()).isEqualTo(204);
                verify(removeTeamFromEventUseCase).execute(any());
        }

        @Test
        void deleteEvent_returns204() {
                ResponseEntity<Void> response = controller.deleteEvent(5L);

                assertThat(response.getStatusCode().value()).isEqualTo(204);
                verify(deleteEventUseCase).execute(any());
        }
}
