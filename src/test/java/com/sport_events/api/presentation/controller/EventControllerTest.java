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
import com.sport_events.api.application.dto.result.VenueResult;
import com.sport_events.api.application.usecase.GetEventUseCase;
import com.sport_events.api.presentation.dto.EventDetailsResponse;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    @Mock
    private GetEventUseCase getEventUseCase;

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
}
