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

import com.sport_events.api.application.dto.query.GetPreviousEventsQuery;
import com.sport_events.api.application.dto.result.EventResult;
import com.sport_events.api.application.dto.result.EventTranslationResult;
import com.sport_events.api.application.dto.result.VenueResult;
import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.repository.EventRepository;

@ExtendWith(MockitoExtension.class)
class GetPreviousEventsUseCaseTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventResultBuilder eventResultBuilder;

    private GetPreviousEventsUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetPreviousEventsUseCase(eventRepository, eventResultBuilder);
    }

    @Test
    void execute_returnsOnlyPreviousEvents() {
        OffsetDateTime now = OffsetDateTime.now();
        Event previousEvent = new Event(1L, now.minusDays(2), 1);
        Event futureEvent = new Event(2L, now.plusDays(2), 2);

        EventResult previousResult = new EventResult(
                1L,
                previousEvent.getStartsAt(),
                new VenueResult(1, "Bernabeu", "Madrid"),
                List.of(),
                new EventTranslationResult(1, 1, "Previous Event", "Description"));

        when(eventRepository.findAll()).thenReturn(List.of(previousEvent, futureEvent));
        when(eventResultBuilder.build(previousEvent, "en")).thenReturn(previousResult);

        var result = useCase.execute(new GetPreviousEventsQuery("en"));

        assertThat(result.events()).hasSize(1);
        assertThat(result.events().getFirst().eventId()).isEqualTo(1L);
        verify(eventRepository).findAll();
    }

    @Test
    void execute_returnsEmptyWhenNoPreviousEvents() {
        OffsetDateTime now = OffsetDateTime.now();
        Event futureEvent = new Event(2L, now.plusDays(2), 2);

        when(eventRepository.findAll()).thenReturn(List.of(futureEvent));

        var result = useCase.execute(new GetPreviousEventsQuery("en"));

        assertThat(result.events()).isEmpty();
        verify(eventRepository).findAll();
    }
}
