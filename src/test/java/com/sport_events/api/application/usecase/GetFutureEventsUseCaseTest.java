package com.sport_events.api.application.usecase;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.application.dto.query.GetFutureEventsQuery;
import com.sport_events.api.application.dto.result.EventResult;
import com.sport_events.api.application.dto.result.EventTranslationResult;
import com.sport_events.api.application.dto.result.VenueResult;
import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.repository.EventRepository;

@ExtendWith(MockitoExtension.class)
class GetFutureEventsUseCaseTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventResultBuilder eventResultBuilder;

    private GetFutureEventsUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetFutureEventsUseCase(eventRepository, eventResultBuilder);
    }

    @Test
    void execute_returnsOnlyFutureEvents() {
        OffsetDateTime now = OffsetDateTime.now();
        Event previousEvent = new Event(1L, now.minusDays(2), 1);
        Event futureEvent = new Event(2L, now.plusDays(2), 2);

        EventResult futureResult = new EventResult(
                2L,
                futureEvent.getStartsAt(),
                new VenueResult(2, "Camp Nou", "Barcelona"),
                List.of(),
                new EventTranslationResult(2, 1, "Future Event", "Description"));

        when(eventRepository.findAll()).thenReturn(List.of(previousEvent, futureEvent));
        when(eventResultBuilder.build(futureEvent, "en")).thenReturn(futureResult);

        var result = useCase.execute(new GetFutureEventsQuery("en"));

        assertThat(result.events()).hasSize(1);
        assertThat(result.events().getFirst().eventId()).isEqualTo(2L);
        verify(eventRepository).findAll();
    }

    @Test
    void execute_returnsEmptyWhenNoFutureEvents() {
        OffsetDateTime now = OffsetDateTime.now();
        Event previousEvent = new Event(1L, now.minusDays(2), 1);

        when(eventRepository.findAll()).thenReturn(List.of(previousEvent));

        var result = useCase.execute(new GetFutureEventsQuery("en"));

        assertThat(result.events()).isEmpty();
        verify(eventRepository).findAll();
    }
}
