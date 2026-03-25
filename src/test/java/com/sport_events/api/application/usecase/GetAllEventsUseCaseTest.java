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

import com.sport_events.api.application.dto.query.GetAllEventsQuery;
import com.sport_events.api.application.dto.result.EventResult;
import com.sport_events.api.application.dto.result.EventTranslationResult;
import com.sport_events.api.application.dto.result.VenueResult;
import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.repository.EventRepository;

@ExtendWith(MockitoExtension.class)
class GetAllEventsUseCaseTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventResultBuilder eventResultBuilder;

    private GetAllEventsUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetAllEventsUseCase(eventRepository, eventResultBuilder);
    }

    @Test
    void execute_returnsAllEvents() {
        Event firstEvent = new Event(1L, OffsetDateTime.parse("2026-04-05T20:00:00Z"), 1);
        Event secondEvent = new Event(2L, OffsetDateTime.parse("2026-05-05T20:00:00Z"), 2);

        EventResult firstResult = new EventResult(
                1L,
                firstEvent.getStartsAt(),
                new VenueResult(1, "Bernabeu", "Madrid"),
                List.of(),
                new EventTranslationResult(1, 1, "Event 1", "Description 1"));
        EventResult secondResult = new EventResult(
                2L,
                secondEvent.getStartsAt(),
                new VenueResult(2, "Camp Nou", "Barcelona"),
                List.of(),
                new EventTranslationResult(2, 1, "Event 2", "Description 2"));

        when(eventRepository.findAll()).thenReturn(List.of(firstEvent, secondEvent));
        when(eventResultBuilder.build(firstEvent, "en")).thenReturn(firstResult);
        when(eventResultBuilder.build(secondEvent, "en")).thenReturn(secondResult);

        var result = useCase.execute(new GetAllEventsQuery("en"));

        assertThat(result.events()).hasSize(2);
        assertThat(result.events().get(0).eventId()).isEqualTo(1L);
        assertThat(result.events().get(1).eventId()).isEqualTo(2L);
        verify(eventRepository).findAll();
    }

    @Test
    void execute_returnsEmptyWhenNoEvents() {
        when(eventRepository.findAll()).thenReturn(List.of());

        var result = useCase.execute(new GetAllEventsQuery("en"));

        assertThat(result.events()).isEmpty();
        verify(eventRepository).findAll();
    }
}
