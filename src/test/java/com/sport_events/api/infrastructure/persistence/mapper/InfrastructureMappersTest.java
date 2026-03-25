package com.sport_events.api.infrastructure.persistence.mapper;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.model.Venue;
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

class InfrastructureMappersTest {

    @Test
    void eventMapper_mapsBothDirections() {
        EventJpaEntity entity = new EventJpaEntity();
        entity.setEventId(1L);
        entity.setStartsAt(OffsetDateTime.parse("2026-04-05T20:00:00Z"));
        VenueJpaEntity venueEntity = new VenueJpaEntity();
        venueEntity.setVenueId(10);
        entity.setVenue(venueEntity);

        Event domain = EventMapper.toDomain(entity);
        assertThat(domain.getEventId()).isEqualTo(1L);
        assertThat(domain.getVenueId()).isEqualTo(10);

        Event roundtripDomain = new Event(2L, OffsetDateTime.parse("2026-05-05T10:00:00Z"), 20);
        VenueJpaEntity roundtripVenue = new VenueJpaEntity();
        roundtripVenue.setVenueId(20);
        EventJpaEntity mappedEntity = EventMapper.toEntity(roundtripDomain, roundtripVenue);
        assertThat(mappedEntity.getEventId()).isEqualTo(2L);
        assertThat(mappedEntity.getStartsAt()).isEqualTo(OffsetDateTime.parse("2026-05-05T10:00:00Z"));
        assertThat(mappedEntity.getVenue()).isSameAs(roundtripVenue);
    }

    @Test
    void eventMapper_handlesNullVenue() {
        EventJpaEntity entity = new EventJpaEntity();
        entity.setEventId(3L);
        entity.setStartsAt(OffsetDateTime.parse("2026-04-05T20:00:00Z"));
        entity.setVenue(null);

        Event domain = EventMapper.toDomain(entity);
        assertThat(domain.getVenueId()).isNull();
    }

    @Test
    void venueMapper_mapsBothDirections() {
        VenueJpaEntity entity = new VenueJpaEntity();
        entity.setVenueId(1);
        entity.setName("Arena");
        entity.setAddress("Address");

        Venue domain = VenueMapper.toDomain(entity);
        assertThat(domain.getVenueId()).isEqualTo(1);
        assertThat(domain.getName()).isEqualTo("Arena");

        VenueJpaEntity mappedEntity = VenueMapper.toEntity(new Venue(2, "Stadium", "Road"));
        assertThat(mappedEntity.getVenueId()).isEqualTo(2);
        assertThat(mappedEntity.getName()).isEqualTo("Stadium");
        assertThat(mappedEntity.getAddress()).isEqualTo("Road");
    }

    @Test
    void eventTeamMapper_mapsAndHandlesNullRelations() {
        EventTeamJpaEntity entity = new EventTeamJpaEntity();
        entity.setEventTeamsId(11);
        EventJpaEntity event = new EventJpaEntity();
        event.setEventId(100L);
        TeamJpaEntity team = new TeamJpaEntity();
        team.setTeamId(7);
        entity.setEvent(event);
        entity.setTeam(team);

        var domain = EventTeamMapper.toDomain(entity);
        assertThat(domain.getEventId()).isEqualTo(100L);
        assertThat(domain.getTeamId()).isEqualTo(7);

        entity.setEvent(null);
        entity.setTeam(null);
        var withNulls = EventTeamMapper.toDomain(entity);
        assertThat(withNulls.getEventId()).isNull();
        assertThat(withNulls.getTeamId()).isNull();
    }

    @Test
    void eventPlayerMapper_mapsAndHandlesNullRelations() {
        EventPlayerJpaEntity entity = new EventPlayerJpaEntity();
        entity.setEventPlayersId(20);
        EventTeamJpaEntity eventTeam = new EventTeamJpaEntity();
        eventTeam.setEventTeamsId(11);
        PlayerJpaEntity player = new PlayerJpaEntity();
        player.setPlayerId(100);
        entity.setEventTeam(eventTeam);
        entity.setPlayer(player);

        var domain = EventPlayerMapper.toDomain(entity);
        assertThat(domain.getEventTeamId()).isEqualTo(11);
        assertThat(domain.getPlayerId()).isEqualTo(100);

        entity.setEventTeam(null);
        entity.setPlayer(null);
        var withNulls = EventPlayerMapper.toDomain(entity);
        assertThat(withNulls.getEventTeamId()).isNull();
        assertThat(withNulls.getPlayerId()).isNull();
    }

    @Test
    void eventTranslationMapper_mapsAndHandlesNullRelations() {
        EventTranslationJpaEntity entity = new EventTranslationJpaEntity();
        entity.setEventTranslationId(30);
        entity.setName("Name");
        entity.setDescription("Description");
        EventJpaEntity event = new EventJpaEntity();
        event.setEventId(101L);
        LanguageJpaEntity language = new LanguageJpaEntity();
        language.setLanguageId(3);
        entity.setEvent(event);
        entity.setLanguage(language);

        var domain = EventTranslationMapper.toDomain(entity);
        assertThat(domain.getEventId()).isEqualTo(101L);
        assertThat(domain.getLanguageId()).isEqualTo(3);

        entity.setEvent(null);
        entity.setLanguage(null);
        var withNulls = EventTranslationMapper.toDomain(entity);
        assertThat(withNulls.getEventId()).isNull();
        assertThat(withNulls.getLanguageId()).isNull();
    }

    @Test
    void languageMapper_mapsFields() {
        LanguageJpaEntity entity = new LanguageJpaEntity();
        entity.setLanguageId(1);
        entity.setCode("en");
        entity.setName("English");

        var domain = LanguageMapper.toDomain(entity);
        assertThat(domain.getLanguageId()).isEqualTo(1);
        assertThat(domain.getCode()).isEqualTo("en");
        assertThat(domain.getName()).isEqualTo("English");
    }

    @Test
    void playerMapper_mapsFields() {
        PlayerJpaEntity entity = new PlayerJpaEntity();
        entity.setPlayerId(100);
        entity.setFirstName("Carlos");
        entity.setLastName("Silva");
        entity.setDateOfBirth(LocalDate.parse("1990-03-15"));

        var domain = PlayerMapper.toDomain(entity);
        assertThat(domain.getPlayerId()).isEqualTo(100);
        assertThat(domain.getFirstName()).isEqualTo("Carlos");
        assertThat(domain.getDateOfBirth()).isEqualTo(LocalDate.parse("1990-03-15"));
    }

    @Test
    void teamMapper_mapsAndHandlesNullSport() {
        TeamJpaEntity entity = new TeamJpaEntity();
        entity.setTeamId(7);
        entity.setName("Real Madrid");
        SportJpaEntity sport = new SportJpaEntity();
        sport.setSportId(1);
        entity.setSport(sport);

        var domain = TeamMapper.toDomain(entity);
        assertThat(domain.getSportId()).isEqualTo(1);

        entity.setSport(null);
        var withNullSport = TeamMapper.toDomain(entity);
        assertThat(withNullSport.getSportId()).isNull();
    }

    @Test
    void sportTranslationMapper_mapsAndHandlesNullRelations() {
        SportTranslationJpaEntity entity = new SportTranslationJpaEntity();
        entity.setSportTranslationId(31);
        entity.setName("Football");
        SportJpaEntity sport = new SportJpaEntity();
        sport.setSportId(1);
        LanguageJpaEntity language = new LanguageJpaEntity();
        language.setLanguageId(1);
        entity.setSport(sport);
        entity.setLanguage(language);

        var domain = SportTranslationMapper.toDomain(entity);
        assertThat(domain.getSportId()).isEqualTo(1);
        assertThat(domain.getLanguageId()).isEqualTo(1);

        entity.setSport(null);
        entity.setLanguage(null);
        var withNulls = SportTranslationMapper.toDomain(entity);
        assertThat(withNulls.getSportId()).isNull();
        assertThat(withNulls.getLanguageId()).isNull();
    }

    @Test
    void sportMapper_mapsFields() {
        SportJpaEntity entity = new SportJpaEntity();
        entity.setSportId(5);

        var domain = SportMapper.toDomain(entity);
        assertThat(domain.getSportId()).isEqualTo(5);
    }

    @Test
    void teamPlayerMapper_mapsAndHandlesNullRelations() {
        TeamPlayerJpaEntity entity = new TeamPlayerJpaEntity();
        entity.setTeamPlayerId(1);
        TeamJpaEntity team = new TeamJpaEntity();
        team.setTeamId(7);
        PlayerJpaEntity player = new PlayerJpaEntity();
        player.setPlayerId(100);
        entity.setTeam(team);
        entity.setPlayer(player);

        var domain = TeamPlayerMapper.toDomain(entity);
        assertThat(domain.getTeamPlayerId()).isEqualTo(1);
        assertThat(domain.getTeamId()).isEqualTo(7);
        assertThat(domain.getPlayerId()).isEqualTo(100);

        entity.setTeam(null);
        entity.setPlayer(null);
        var withNulls = TeamPlayerMapper.toDomain(entity);
        assertThat(withNulls.getTeamId()).isNull();
        assertThat(withNulls.getPlayerId()).isNull();
    }
}
