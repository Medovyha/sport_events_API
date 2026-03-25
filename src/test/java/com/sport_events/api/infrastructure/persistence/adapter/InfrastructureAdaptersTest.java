package com.sport_events.api.infrastructure.persistence.adapter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.infrastructure.persistence.jpa.entity.EventJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.EventPlayerJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.EventTeamJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.EventTranslationJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.LanguageJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.PlayerJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.SportJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.SportTranslationJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.TeamJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.TeamPlayerJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.VenueJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.repository.EventJpaRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.EventPlayerJpaRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.EventTeamJpaRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.EventTranslationJpaRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.LanguageJpaRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.PlayerJpaRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.SportJpaRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.SportTranslationJpaRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.TeamJpaRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.TeamPlayerJpaRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.VenueJpaRepository;

@ExtendWith(MockitoExtension.class)
class InfrastructureAdaptersTest {

    @Mock
    private EventJpaRepository eventJpaRepository;
    @Mock
    private EventPlayerJpaRepository eventPlayerJpaRepository;
    @Mock
    private EventTeamJpaRepository eventTeamJpaRepository;
    @Mock
    private EventTranslationJpaRepository eventTranslationJpaRepository;
    @Mock
    private LanguageJpaRepository languageJpaRepository;
    @Mock
    private PlayerJpaRepository playerJpaRepository;
    @Mock
    private SportJpaRepository sportJpaRepository;
    @Mock
    private SportTranslationJpaRepository sportTranslationJpaRepository;
    @Mock
    private TeamJpaRepository teamJpaRepository;
    @Mock
    private TeamPlayerJpaRepository teamPlayerJpaRepository;
    @Mock
    private VenueJpaRepository venueJpaRepository;

    @InjectMocks
    private EventRepositoryAdapter eventRepositoryAdapter;
    @InjectMocks
    private EventPlayerRepositoryAdapter eventPlayerRepositoryAdapter;
    @InjectMocks
    private EventTeamRepositoryAdapter eventTeamRepositoryAdapter;
    @InjectMocks
    private EventTranslationRepositoryAdapter eventTranslationRepositoryAdapter;
    @InjectMocks
    private LanguageRepositoryAdapter languageRepositoryAdapter;
    @InjectMocks
    private PlayerRepositoryAdapter playerRepositoryAdapter;
    @InjectMocks
    private SportRepositoryAdapter sportRepositoryAdapter;
    @InjectMocks
    private SportTranslationRepositoryAdapter sportTranslationRepositoryAdapter;
    @InjectMocks
    private TeamRepositoryAdapter teamRepositoryAdapter;
    @InjectMocks
    private TeamPlayerRepositoryAdapter teamPlayerRepositoryAdapter;
    @InjectMocks
    private VenueRepositoryAdapter venueRepositoryAdapter;

    @Test
    void eventAdapter_mapsFindAndDelegatesDelete_andSaveThrows() {
        EventJpaEntity event = new EventJpaEntity();
        event.setEventId(1L);
        event.setStartsAt(OffsetDateTime.parse("2026-04-05T20:00:00Z"));
        VenueJpaEntity venue = new VenueJpaEntity();
        venue.setVenueId(10);
        event.setVenue(venue);
        when(eventJpaRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventJpaRepository.findAll()).thenReturn(List.of(event));

        assertThat(eventRepositoryAdapter.findById(1L)).isPresent();
        assertThat(eventRepositoryAdapter.findAll()).hasSize(1);

        eventRepositoryAdapter.deleteById(1L);
        verify(eventJpaRepository).deleteById(1L);

        assertThatThrownBy(() -> eventRepositoryAdapter.save(null))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void eventPlayerAdapter_coversReadPathsDeleteAndSave() {
        EventPlayerJpaEntity entity = new EventPlayerJpaEntity();
        entity.setEventPlayersId(1);
        EventTeamJpaEntity eventTeam = new EventTeamJpaEntity();
        eventTeam.setEventTeamsId(11);
        PlayerJpaEntity player = new PlayerJpaEntity();
        player.setPlayerId(100);
        entity.setEventTeam(eventTeam);
        entity.setPlayer(player);

        when(eventPlayerJpaRepository.findById(1)).thenReturn(Optional.of(entity));
        when(eventPlayerJpaRepository.findAll()).thenReturn(List.of(entity));
        when(eventPlayerJpaRepository.findByEventTeam_EventTeamsId(11)).thenReturn(List.of(entity));
        when(eventPlayerJpaRepository.findByEventTeam_EventTeamsIdAndPlayer_PlayerId(11, 100)).thenReturn(Optional.of(entity));

        assertThat(eventPlayerRepositoryAdapter.findById(1)).isPresent();
        assertThat(eventPlayerRepositoryAdapter.findAll()).hasSize(1);
        assertThat(eventPlayerRepositoryAdapter.findByEventTeamId(11)).hasSize(1);
        assertThat(eventPlayerRepositoryAdapter.findByEventTeamIdAndPlayerId(11, 100)).isPresent();

        eventPlayerRepositoryAdapter.deleteById(1);
        verify(eventPlayerJpaRepository).deleteById(1);

        assertThatThrownBy(() -> eventPlayerRepositoryAdapter.save(null))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void eventTeamAdapter_coversReadPathsDeleteAndSave() {
        EventTeamJpaEntity entity = new EventTeamJpaEntity();
        entity.setEventTeamsId(11);
        EventJpaEntity event = new EventJpaEntity();
        event.setEventId(1L);
        TeamJpaEntity team = new TeamJpaEntity();
        team.setTeamId(7);
        entity.setEvent(event);
        entity.setTeam(team);

        when(eventTeamJpaRepository.findById(11)).thenReturn(Optional.of(entity));
        when(eventTeamJpaRepository.findAll()).thenReturn(List.of(entity));
        when(eventTeamJpaRepository.findByEvent_EventId(1L)).thenReturn(List.of(entity));
        when(eventTeamJpaRepository.findByEvent_EventIdAndTeam_TeamId(1L, 7)).thenReturn(Optional.of(entity));

        assertThat(eventTeamRepositoryAdapter.findById(11)).isPresent();
        assertThat(eventTeamRepositoryAdapter.findAll()).hasSize(1);
        assertThat(eventTeamRepositoryAdapter.findByEventId(1L)).hasSize(1);
        assertThat(eventTeamRepositoryAdapter.findByEventIdAndTeamId(1L, 7)).isPresent();

        eventTeamRepositoryAdapter.deleteById(11);
        verify(eventTeamJpaRepository).deleteById(11);

        assertThatThrownBy(() -> eventTeamRepositoryAdapter.save(null))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void eventTranslationAdapter_coversReadPathsDeleteAndSave() {
        EventTranslationJpaEntity entity = new EventTranslationJpaEntity();
        entity.setEventTranslationId(50);
        entity.setName("El Clásico");
        entity.setDescription("Desc");
        EventJpaEntity event = new EventJpaEntity();
        event.setEventId(1L);
        LanguageJpaEntity language = new LanguageJpaEntity();
        language.setLanguageId(1);
        entity.setEvent(event);
        entity.setLanguage(language);

        when(eventTranslationJpaRepository.findById(50)).thenReturn(Optional.of(entity));
        when(eventTranslationJpaRepository.findAll()).thenReturn(List.of(entity));
        when(eventTranslationJpaRepository.findByEvent_EventId(1L)).thenReturn(List.of(entity));
        when(eventTranslationJpaRepository.findByLanguage_LanguageId(1)).thenReturn(List.of(entity));
        when(eventTranslationJpaRepository.findByEvent_EventIdAndLanguage_LanguageId(1L, 1)).thenReturn(Optional.of(entity));

        assertThat(eventTranslationRepositoryAdapter.findById(50)).isPresent();
        assertThat(eventTranslationRepositoryAdapter.findAll()).hasSize(1);
        assertThat(eventTranslationRepositoryAdapter.findByEventId(1L)).hasSize(1);
        assertThat(eventTranslationRepositoryAdapter.findByLanguageId(1)).hasSize(1);
        assertThat(eventTranslationRepositoryAdapter.findByEventIdAndLanguageId(1L, 1)).isPresent();

        eventTranslationRepositoryAdapter.deleteById(50);
        verify(eventTranslationJpaRepository).deleteById(50);

        assertThatThrownBy(() -> eventTranslationRepositoryAdapter.save(null))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void languageAdapter_coversReadPathsDeleteAndSave() {
        LanguageJpaEntity entity = new LanguageJpaEntity();
        entity.setLanguageId(1);
        entity.setCode("en");
        entity.setName("English");

        when(languageJpaRepository.findById(1)).thenReturn(Optional.of(entity));
        when(languageJpaRepository.findAll()).thenReturn(List.of(entity));
        when(languageJpaRepository.findByCode("en")).thenReturn(Optional.of(entity));

        assertThat(languageRepositoryAdapter.findById(1)).isPresent();
        assertThat(languageRepositoryAdapter.findAll()).hasSize(1);
        assertThat(languageRepositoryAdapter.findByCode("en")).isPresent();

        languageRepositoryAdapter.deleteById(1);
        verify(languageJpaRepository).deleteById(1);

        assertThatThrownBy(() -> languageRepositoryAdapter.save(null))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void playerAdapter_coversReadPathsDeleteAndSave() {
        PlayerJpaEntity entity = new PlayerJpaEntity();
        entity.setPlayerId(100);
        entity.setFirstName("Carlos");
        entity.setLastName("Silva");
        entity.setDateOfBirth(LocalDate.parse("1990-03-15"));

        when(playerJpaRepository.findById(100)).thenReturn(Optional.of(entity));
        when(playerJpaRepository.findAll()).thenReturn(List.of(entity));

        assertThat(playerRepositoryAdapter.findById(100)).isPresent();
        assertThat(playerRepositoryAdapter.findAll()).hasSize(1);

        playerRepositoryAdapter.deleteById(100);
        verify(playerJpaRepository).deleteById(100);

        PlayerJpaEntity savedEntity = new PlayerJpaEntity();
        savedEntity.setPlayerId(100);
        savedEntity.setFirstName("Carlos");
        savedEntity.setLastName("Silva");
        savedEntity.setDateOfBirth(LocalDate.parse("1990-03-15"));
        when(playerJpaRepository.save(any())).thenReturn(savedEntity);
        var player = new com.sport_events.api.domain.model.Player(null, "Carlos", "Silva", LocalDate.parse("1990-03-15"));
        var saved = playerRepositoryAdapter.save(player);
        assertThat(saved.getFirstName()).isEqualTo("Carlos");
    }

    @Test
    void sportTranslationAdapter_coversReadPathsDeleteAndSave() {
        SportTranslationJpaEntity entity = new SportTranslationJpaEntity();
        entity.setSportTranslationId(31);
        entity.setName("Football");
        SportJpaEntity sport = new SportJpaEntity();
        sport.setSportId(1);
        LanguageJpaEntity language = new LanguageJpaEntity();
        language.setLanguageId(1);
        entity.setSport(sport);
        entity.setLanguage(language);

        when(sportTranslationJpaRepository.findById(31)).thenReturn(Optional.of(entity));
        when(sportTranslationJpaRepository.findAll()).thenReturn(List.of(entity));
        when(sportTranslationJpaRepository.findBySport_SportId(1)).thenReturn(List.of(entity));
        when(sportTranslationJpaRepository.findByLanguage_LanguageId(1)).thenReturn(List.of(entity));
        when(sportTranslationJpaRepository.findBySport_SportIdAndLanguage_LanguageId(1, 1)).thenReturn(Optional.of(entity));

        assertThat(sportTranslationRepositoryAdapter.findById(31)).isPresent();
        assertThat(sportTranslationRepositoryAdapter.findAll()).hasSize(1);
        assertThat(sportTranslationRepositoryAdapter.findBySportId(1)).hasSize(1);
        assertThat(sportTranslationRepositoryAdapter.findByLanguageId(1)).hasSize(1);
        assertThat(sportTranslationRepositoryAdapter.findBySportIdAndLanguageId(1, 1)).isPresent();

        sportTranslationRepositoryAdapter.deleteById(31);
        verify(sportTranslationJpaRepository).deleteById(31);

        SportTranslationJpaEntity savedSt = new SportTranslationJpaEntity();
        savedSt.setSportTranslationId(31);
        savedSt.setName("Football");
        savedSt.setSport(sport);
        savedSt.setLanguage(language);
        when(sportTranslationJpaRepository.save(any())).thenReturn(savedSt);
        var st = new com.sport_events.api.domain.model.SportTranslation(null, 1, 1, "Football");
        var savedResult = sportTranslationRepositoryAdapter.save(st);
        assertThat(savedResult.getName()).isEqualTo("Football");
    }

    @Test
    void teamAdapter_coversReadPathsDeleteAndSave() {
        TeamJpaEntity entity = new TeamJpaEntity();
        entity.setTeamId(7);
        entity.setName("Real Madrid");
        SportJpaEntity sport = new SportJpaEntity();
        sport.setSportId(1);
        entity.setSport(sport);

        when(teamJpaRepository.findById(7)).thenReturn(Optional.of(entity));
        when(teamJpaRepository.findAll()).thenReturn(List.of(entity));
        when(teamJpaRepository.findBySport_SportId(1)).thenReturn(List.of(entity));

        assertThat(teamRepositoryAdapter.findById(7)).isPresent();
        assertThat(teamRepositoryAdapter.findAll()).hasSize(1);
        assertThat(teamRepositoryAdapter.findBySportId(1)).hasSize(1);

        teamRepositoryAdapter.deleteById(7);
        verify(teamJpaRepository).deleteById(7);

        TeamJpaEntity savedTeam = new TeamJpaEntity();
        savedTeam.setTeamId(7);
        savedTeam.setName("Real Madrid");
        savedTeam.setSport(sport);
        when(teamJpaRepository.save(any())).thenReturn(savedTeam);
        var teamDomain = new com.sport_events.api.domain.model.Team(null, "Real Madrid", 1);
        var savedTeamResult = teamRepositoryAdapter.save(teamDomain);
        assertThat(savedTeamResult.getName()).isEqualTo("Real Madrid");
    }

    @Test
    void venueAdapter_coversReadPathsDeleteAndSave() {
        VenueJpaEntity entity = new VenueJpaEntity();
        entity.setVenueId(1);
        entity.setName("Arena");
        entity.setAddress("Address");

        when(venueJpaRepository.findById(1)).thenReturn(Optional.of(entity));
        when(venueJpaRepository.findAll()).thenReturn(List.of(entity));

        assertThat(venueRepositoryAdapter.findById(1)).isPresent();
        assertThat(venueRepositoryAdapter.findAll()).hasSize(1);

        venueRepositoryAdapter.deleteById(1);
        verify(venueJpaRepository).deleteById(1);

        VenueJpaEntity savedVenue = new VenueJpaEntity();
        savedVenue.setVenueId(1);
        savedVenue.setName("Arena");
        savedVenue.setAddress("Address");
        when(venueJpaRepository.save(any())).thenReturn(savedVenue);
        var venueDomain = new com.sport_events.api.domain.model.Venue(null, "Arena", "Address");
        var savedVenueResult = venueRepositoryAdapter.save(venueDomain);
        assertThat(savedVenueResult.getName()).isEqualTo("Arena");
    }

    @Test
    void sportAdapter_coversReadPathsDeleteAndSave() {
        SportJpaEntity entity = new SportJpaEntity();
        entity.setSportId(1);

        when(sportJpaRepository.findById(1)).thenReturn(Optional.of(entity));
        when(sportJpaRepository.findAll()).thenReturn(List.of(entity));

        assertThat(sportRepositoryAdapter.findById(1)).isPresent();
        assertThat(sportRepositoryAdapter.findAll()).hasSize(1);

        sportRepositoryAdapter.deleteById(1);
        verify(sportJpaRepository).deleteById(1);

        SportJpaEntity savedSport = new SportJpaEntity();
        savedSport.setSportId(1);
        when(sportJpaRepository.save(any())).thenReturn(savedSport);
        var savedSportResult = sportRepositoryAdapter.save(new com.sport_events.api.domain.model.Sport(null));
        assertThat(savedSportResult).isNotNull();
    }

    @Test
    void teamPlayerAdapter_coversReadPathsDeleteAndSave() {
        TeamPlayerJpaEntity entity = new TeamPlayerJpaEntity();
        entity.setTeamPlayerId(1);
        TeamJpaEntity team = new TeamJpaEntity();
        team.setTeamId(7);
        PlayerJpaEntity player = new PlayerJpaEntity();
        player.setPlayerId(100);
        entity.setTeam(team);
        entity.setPlayer(player);

        when(teamPlayerJpaRepository.findById(1)).thenReturn(Optional.of(entity));
        when(teamPlayerJpaRepository.findAll()).thenReturn(List.of(entity));
        when(teamPlayerJpaRepository.findByTeam_TeamId(7)).thenReturn(List.of(entity));
        when(teamPlayerJpaRepository.findByPlayer_PlayerId(100)).thenReturn(List.of(entity));
        when(teamPlayerJpaRepository.findByTeam_TeamIdAndPlayer_PlayerId(7, 100)).thenReturn(Optional.of(entity));

        assertThat(teamPlayerRepositoryAdapter.findById(1)).isPresent();
        assertThat(teamPlayerRepositoryAdapter.findAll()).hasSize(1);
        assertThat(teamPlayerRepositoryAdapter.findByTeamId(7)).hasSize(1);
        assertThat(teamPlayerRepositoryAdapter.findByPlayerId(100)).hasSize(1);
        assertThat(teamPlayerRepositoryAdapter.findByTeamIdAndPlayerId(7, 100)).isPresent();

        teamPlayerRepositoryAdapter.deleteById(1);
        verify(teamPlayerJpaRepository).deleteById(1);

        TeamPlayerJpaEntity savedTp = new TeamPlayerJpaEntity();
        savedTp.setTeamPlayerId(1);
        savedTp.setTeam(team);
        savedTp.setPlayer(player);
        when(teamPlayerJpaRepository.save(any())).thenReturn(savedTp);
        var tp = new com.sport_events.api.domain.model.TeamPlayer(null, 7, 100);
        var savedTpResult = teamPlayerRepositoryAdapter.save(tp);
        assertThat(savedTpResult.getTeamId()).isEqualTo(7);
    }
}
