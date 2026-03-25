package com.sport_events.api.application.usecase;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.application.dto.command.EventTranslationCommand;
import com.sport_events.api.application.dto.command.UpdateEventCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.model.EventTranslation;
import com.sport_events.api.domain.model.Language;
import com.sport_events.api.domain.model.Venue;
import com.sport_events.api.domain.repository.EventRepository;
import com.sport_events.api.domain.repository.EventTranslationRepository;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.VenueRepository;

@ExtendWith(MockitoExtension.class)
class UpdateEventUseCaseTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventTranslationRepository eventTranslationRepository;
    @Mock
    private LanguageRepository languageRepository;
    @Mock
    private VenueRepository venueRepository;

    private UpdateEventUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateEventUseCase(eventRepository, eventTranslationRepository, languageRepository, venueRepository);
    }

    @Test
    void execute_updatesEventAndTranslations() {
        OffsetDateTime startsAt = OffsetDateTime.parse("2026-04-05T20:00:00Z");
        when(eventRepository.findById(5L)).thenReturn(Optional.of(new Event(5L, startsAt, 10)));
        when(venueRepository.findById(10)).thenReturn(Optional.of(new Venue(10, "Bernabeu", "Madrid")));
        when(eventRepository.save(any())).thenReturn(new Event(5L, startsAt, 10));
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(new Language(1, "en", "English")));
        when(eventTranslationRepository.findByEventIdAndLanguageId(5L, 1))
                .thenReturn(Optional.of(new EventTranslation(11, 5L, 1, "Old", "Old")));
        when(eventTranslationRepository.save(any()))
                .thenReturn(new EventTranslation(11, 5L, 1, "New", "New desc"));

        var result = useCase.execute(new UpdateEventCommand(5L, startsAt, 10, List.of(
                new EventTranslationCommand("en", "New", "New desc"))));

        assertThat(result.eventId()).isEqualTo(5L);
        assertThat(result.translation()).isNotNull();
        assertThat(result.translation().name()).isEqualTo("New");
    }

    @Test
    void execute_throwsWhenEventMissing() {
        OffsetDateTime startsAt = OffsetDateTime.parse("2026-04-05T20:00:00Z");
        when(eventRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new UpdateEventCommand(5L, startsAt, 10, List.of(
                new EventTranslationCommand("en", "New", "New desc")))))
                .isInstanceOf(DomainException.class)
                .hasMessage("Event not found: 5");
    }
}
