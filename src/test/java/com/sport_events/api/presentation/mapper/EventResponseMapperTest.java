package com.sport_events.api.presentation.mapper;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import com.sport_events.api.application.dto.result.EventResult;
import com.sport_events.api.application.dto.result.EventTeamResult;
import com.sport_events.api.application.dto.result.EventTranslationResult;
import com.sport_events.api.application.dto.result.PlayerResult;
import com.sport_events.api.application.dto.result.VenueResult;
import com.sport_events.api.presentation.dto.EventDetailsResponse;

class EventResponseMapperTest {

    @Test
    void toResponse_flattensTranslationAndMapsNestedTeams() {
        EventResult result = new EventResult(
                1L,
                OffsetDateTime.parse("2026-04-05T20:00:00Z"),
                new VenueResult(1, "Santiago Bernabéu", "Madrid"),
                List.of(new EventTeamResult(
                        11,
                        7,
                        "Real Madrid",
                        1,
                        "Football",
                        List.of(new PlayerResult(100, "Carlos", "Silva", LocalDate.parse("1990-03-15"), null)))),
                new EventTranslationResult(41, 1, "El Clásico", "English description"));

        EventDetailsResponse response = EventResponseMapper.toResponse(result);

        assertThat(response.name()).isEqualTo("El Clásico");
        assertThat(response.description()).isEqualTo("English description");
        assertThat(response.teams()).hasSize(1);
        assertThat(response.teams().getFirst().teamName()).isEqualTo("Real Madrid");
        assertThat(response.teams().getFirst().sportName()).isEqualTo("Football");
        assertThat(response.teams().getFirst().players()).hasSize(1);
        assertThat(response.teams().getFirst().players().getFirst().lastName()).isEqualTo("Silva");
    }

    @Test
    void toResponse_supportsEventsWithoutTeams() {
        EventResult result = new EventResult(
                1L,
                OffsetDateTime.parse("2026-04-05T20:00:00Z"),
                new VenueResult(1, "Santiago Bernabéu", "Madrid"),
                List.of(),
                new EventTranslationResult(41, 1, "El Clásico", "English description"));

        EventDetailsResponse response = EventResponseMapper.toResponse(result);

        assertThat(response.teams()).isEmpty();
        assertThat(response.name()).isEqualTo("El Clásico");
    }

    @Test
    void toResponse_supportsTeamsWithoutPlayers() {
        EventResult result = new EventResult(
                1L,
                OffsetDateTime.parse("2026-04-05T20:00:00Z"),
                new VenueResult(1, "Santiago Bernabéu", "Madrid"),
                List.of(new EventTeamResult(
                        11,
                        7,
                        "Real Madrid",
                        1,
                        "Football",
                        List.of())),
                new EventTranslationResult(41, 1, "El Clásico", "English description"));

        EventDetailsResponse response = EventResponseMapper.toResponse(result);

        assertThat(response.teams()).hasSize(1);
        assertThat(response.teams().getFirst().players()).isEmpty();
    }
}
