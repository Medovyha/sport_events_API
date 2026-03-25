package com.sport_events.api.application.usecase;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.application.dto.query.GetEventQuery;
import com.sport_events.api.application.dto.result.EventResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.model.EventPlayer;
import com.sport_events.api.domain.model.EventTeam;
import com.sport_events.api.domain.model.EventTranslation;
import com.sport_events.api.domain.model.Language;
import com.sport_events.api.domain.model.Player;
import com.sport_events.api.domain.model.SportTranslation;
import com.sport_events.api.domain.model.Team;
import com.sport_events.api.domain.model.Venue;
import com.sport_events.api.domain.repository.EventPlayerRepository;
import com.sport_events.api.domain.repository.EventRepository;
import com.sport_events.api.domain.repository.EventTeamRepository;
import com.sport_events.api.domain.repository.EventTranslationRepository;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.PlayerRepository;
import com.sport_events.api.domain.repository.SportTranslationRepository;
import com.sport_events.api.domain.repository.TeamRepository;
import com.sport_events.api.domain.repository.VenueRepository;

@ExtendWith(MockitoExtension.class)
class GetEventUseCaseTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private VenueRepository venueRepository;
    @Mock
    private EventTeamRepository eventTeamRepository;
    @Mock
    private EventTranslationRepository eventTranslationRepository;
    @Mock
    private EventPlayerRepository eventPlayerRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private LanguageRepository languageRepository;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private SportTranslationRepository sportTranslationRepository;

    private GetEventUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetEventUseCase(
                eventRepository,
                venueRepository,
                eventTeamRepository,
                eventTranslationRepository,
                eventPlayerRepository,
                playerRepository,
                languageRepository,
                teamRepository,
                sportTranslationRepository);
    }

    @Test
    void execute_returnsLocalizedEventWithTeamAndPlayers() {
        Event event = new Event(1L, OffsetDateTime.parse("2026-04-05T20:00:00Z"), 1);
        Venue venue = new Venue(1, "Santiago Bernabéu", "Madrid");
        Language polish = new Language(3, "pl", "Polish");
        EventTeam eventTeam = new EventTeam(11, 1L, 7);
        Team team = new Team(7, "Real Madrid", 1);
        EventPlayer eventPlayer = new EventPlayer(21, 11, 100);
        Player player = new Player(100, "Carlos", "Silva", LocalDate.parse("1990-03-15"));
        SportTranslation sportTranslation = new SportTranslation(31, 1, 3, "Piłka nożna");
        EventTranslation eventTranslation = new EventTranslation(41, 1L, 3, "El Clásico", "Polish description");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(venueRepository.findById(1)).thenReturn(Optional.of(venue));
        when(languageRepository.findByCode("pl")).thenReturn(Optional.of(polish));
        when(eventTeamRepository.findByEventId(1L)).thenReturn(List.of(eventTeam));
        when(teamRepository.findById(7)).thenReturn(Optional.of(team));
        when(sportTranslationRepository.findBySportIdAndLanguageId(1, 3)).thenReturn(Optional.of(sportTranslation));
        when(eventPlayerRepository.findByEventTeamId(11)).thenReturn(List.of(eventPlayer));
        when(playerRepository.findById(100)).thenReturn(Optional.of(player));
        when(eventTranslationRepository.findByEventIdAndLanguageId(1L, 3)).thenReturn(Optional.of(eventTranslation));

        EventResult result = useCase.execute(new GetEventQuery(1L, "pl"));

        assertThat(result.eventId()).isEqualTo(1L);
        assertThat(result.translation()).isNotNull();
        assertThat(result.translation().name()).isEqualTo("El Clásico");
        assertThat(result.teams()).hasSize(1);
        assertThat(result.teams().getFirst().teamName()).isEqualTo("Real Madrid");
        assertThat(result.teams().getFirst().sportName()).isEqualTo("Piłka nożna");
        assertThat(result.teams().getFirst().players()).hasSize(1);
        assertThat(result.teams().getFirst().players().getFirst().firstName()).isEqualTo("Carlos");
    }

    @Test
    void execute_fallsBackToEnglishWhenRequestedLanguageIsMissing() {
        Event event = new Event(1L, OffsetDateTime.parse("2026-04-05T20:00:00Z"), 1);
        Venue venue = new Venue(1, "Santiago Bernabéu", "Madrid");
        Language english = new Language(1, "en", "English");
        EventTranslation englishTranslation = new EventTranslation(51, 1L, 1, "El Clásico", "English description");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(venueRepository.findById(1)).thenReturn(Optional.of(venue));
        when(languageRepository.findByCode("de")).thenReturn(Optional.empty());
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));
        when(eventTeamRepository.findByEventId(1L)).thenReturn(List.of());
        when(eventTranslationRepository.findByEventIdAndLanguageId(1L, 1)).thenReturn(Optional.of(englishTranslation));

        EventResult result = useCase.execute(new GetEventQuery(1L, "de"));

        assertThat(result.translation()).isNotNull();
        assertThat(result.translation().languageId()).isEqualTo(1);
        assertThat(result.translation().description()).isEqualTo("English description");
        verify(languageRepository).findByCode("de");
        verify(languageRepository).findByCode("en");
    }

    @Test
    void execute_throwsWhenEventIsMissing() {
        when(eventRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new GetEventQuery(999L, "en")))
                .isInstanceOf(DomainException.class)
                .hasMessage("Event not found: 999");
    }

    @Test
    void execute_returnsEmptyTeamsWhenNoTeamsExistInDatabase() {
        Event event = new Event(1L, OffsetDateTime.parse("2026-04-05T20:00:00Z"), 1);
        Venue venue = new Venue(1, "Santiago Bernabéu", "Madrid");
        Language english = new Language(1, "en", "English");
        EventTranslation englishTranslation = new EventTranslation(51, 1L, 1, "El Clásico", "English description");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(venueRepository.findById(1)).thenReturn(Optional.of(venue));
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));
        when(eventTeamRepository.findByEventId(1L)).thenReturn(List.of());
        when(eventTranslationRepository.findByEventIdAndLanguageId(1L, 1)).thenReturn(Optional.of(englishTranslation));

        EventResult result = useCase.execute(new GetEventQuery(1L, "en"));

        assertThat(result.teams()).isEmpty();
    }

    @Test
    void execute_returnsTeamWithEmptyPlayersWhenNoPlayersExistInDatabase() {
        Event event = new Event(1L, OffsetDateTime.parse("2026-04-05T20:00:00Z"), 1);
        Venue venue = new Venue(1, "Santiago Bernabéu", "Madrid");
        Language english = new Language(1, "en", "English");
        EventTeam eventTeam = new EventTeam(11, 1L, 7);
        Team team = new Team(7, "Real Madrid", 1);
        SportTranslation sportTranslation = new SportTranslation(31, 1, 1, "Football");
        EventTranslation englishTranslation = new EventTranslation(51, 1L, 1, "El Clásico", "English description");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(venueRepository.findById(1)).thenReturn(Optional.of(venue));
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));
        when(eventTeamRepository.findByEventId(1L)).thenReturn(List.of(eventTeam));
        when(teamRepository.findById(7)).thenReturn(Optional.of(team));
        when(sportTranslationRepository.findBySportIdAndLanguageId(1, 1)).thenReturn(Optional.of(sportTranslation));
        when(eventPlayerRepository.findByEventTeamId(11)).thenReturn(List.of());
        when(eventTranslationRepository.findByEventIdAndLanguageId(1L, 1)).thenReturn(Optional.of(englishTranslation));

        EventResult result = useCase.execute(new GetEventQuery(1L, "en"));

        assertThat(result.teams()).hasSize(1);
        assertThat(result.teams().getFirst().players()).isEmpty();
    }

    @Test
    void execute_returnsNullTranslationWhenNoLanguageCanBeResolved() {
        Event event = new Event(1L, OffsetDateTime.parse("2026-04-05T20:00:00Z"), 1);
        Venue venue = new Venue(1, "Santiago Bernabéu", "Madrid");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(venueRepository.findById(1)).thenReturn(Optional.of(venue));
        when(languageRepository.findByCode("zz")).thenReturn(Optional.empty());
        when(languageRepository.findByCode("en")).thenReturn(Optional.empty());
        when(eventTeamRepository.findByEventId(1L)).thenReturn(List.of());

        EventResult result = useCase.execute(new GetEventQuery(1L, "zz"));

        assertThat(result.translation()).isNull();
    }

    @Test
    void execute_returnsNullTranslationWhenLanguageExistsButNoTranslationExists() {
        Event event = new Event(1L, OffsetDateTime.parse("2026-04-05T20:00:00Z"), 1);
        Venue venue = new Venue(1, "Santiago Bernabéu", "Madrid");
        Language english = new Language(1, "en", "English");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(venueRepository.findById(1)).thenReturn(Optional.of(venue));
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));
        when(eventTeamRepository.findByEventId(1L)).thenReturn(List.of());
        when(eventTranslationRepository.findByEventIdAndLanguageId(1L, 1)).thenReturn(Optional.empty());

        EventResult result = useCase.execute(new GetEventQuery(1L, "en"));

        assertThat(result.translation()).isNull();
    }

    @Test
    void execute_fallsBackToEnglishSportNameWhenLocalizedSportNameMissing() {
        Event event = new Event(1L, OffsetDateTime.parse("2026-04-05T20:00:00Z"), 1);
        Venue venue = new Venue(1, "Santiago Bernabéu", "Madrid");
        Language polish = new Language(3, "pl", "Polish");
        Language english = new Language(1, "en", "English");
        EventTeam eventTeam = new EventTeam(11, 1L, 7);
        Team team = new Team(7, "Real Madrid", 1);
        SportTranslation englishSport = new SportTranslation(32, 1, 1, "Football");
        EventTranslation eventTranslation = new EventTranslation(41, 1L, 3, "El Clásico", "Polish description");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(venueRepository.findById(1)).thenReturn(Optional.of(venue));
        when(languageRepository.findByCode("pl")).thenReturn(Optional.of(polish));
        when(eventTeamRepository.findByEventId(1L)).thenReturn(List.of(eventTeam));
        when(teamRepository.findById(7)).thenReturn(Optional.of(team));
        when(sportTranslationRepository.findBySportIdAndLanguageId(1, 3)).thenReturn(Optional.empty());
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));
        when(sportTranslationRepository.findBySportIdAndLanguageId(1, 1)).thenReturn(Optional.of(englishSport));
        when(eventPlayerRepository.findByEventTeamId(11)).thenReturn(List.of());
        when(eventTranslationRepository.findByEventIdAndLanguageId(1L, 3)).thenReturn(Optional.of(eventTranslation));

        EventResult result = useCase.execute(new GetEventQuery(1L, "pl"));

        assertThat(result.teams()).hasSize(1);
        assertThat(result.teams().getFirst().sportName()).isEqualTo("Football");
    }

    @Test
    void execute_returnsNullSportNameWhenTeamSportIdIsNull() {
        Event event = new Event(1L, OffsetDateTime.parse("2026-04-05T20:00:00Z"), 1);
        Venue venue = new Venue(1, "Santiago Bernabéu", "Madrid");
        Language english = new Language(1, "en", "English");
        EventTeam eventTeam = new EventTeam(11, 1L, 7);
        Team team = new Team(7, "No Sport Team", null);
        EventTranslation eventTranslation = new EventTranslation(41, 1L, 1, "El Clásico", "English description");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(venueRepository.findById(1)).thenReturn(Optional.of(venue));
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));
        when(eventTeamRepository.findByEventId(1L)).thenReturn(List.of(eventTeam));
        when(teamRepository.findById(7)).thenReturn(Optional.of(team));
        when(eventPlayerRepository.findByEventTeamId(11)).thenReturn(List.of());
        when(eventTranslationRepository.findByEventIdAndLanguageId(1L, 1)).thenReturn(Optional.of(eventTranslation));

        EventResult result = useCase.execute(new GetEventQuery(1L, "en"));

        assertThat(result.teams()).hasSize(1);
        assertThat(result.teams().getFirst().sportName()).isNull();
    }
}
