package com.sport_events.api.presentation.mapper;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import com.sport_events.api.application.dto.result.PlayerResult;
import com.sport_events.api.application.dto.result.SportResult;
import com.sport_events.api.application.dto.result.TeamResult;
import com.sport_events.api.application.dto.result.VenueResult;
import com.sport_events.api.presentation.dto.PlayerResponse;
import com.sport_events.api.presentation.dto.SportResponse;
import com.sport_events.api.presentation.dto.TeamResponse;
import com.sport_events.api.presentation.dto.VenueResponse;

class ResourceResponseMappersTest {

    @Test
    void venueResponseMapper_mapsAllFields() {
        VenueResult result = new VenueResult(1, "Bernabéu", "Madrid");

        VenueResponse response = VenueResponseMapper.toResponse(result);

        assertThat(response.venueId()).isEqualTo(1);
        assertThat(response.name()).isEqualTo("Bernabéu");
        assertThat(response.address()).isEqualTo("Madrid");
    }

    @Test
    void playerResponseMapper_mapsAllFields() {
        PlayerResult result = new PlayerResult(100, "Carlos", "Silva", LocalDate.parse("1990-03-15"), null);

        PlayerResponse response = PlayerResponseMapper.toResponse(result);

        assertThat(response.playerId()).isEqualTo(100);
        assertThat(response.firstName()).isEqualTo("Carlos");
        assertThat(response.lastName()).isEqualTo("Silva");
        assertThat(response.dateOfBirth()).isEqualTo(LocalDate.parse("1990-03-15"));
        assertThat(response.teams()).isNull();
    }

    @Test
    void playerResponseMapper_mapsTeamsList() {
        TeamResult team = new TeamResult(7, "Real Madrid", 1, "Football");
        PlayerResult result = new PlayerResult(100, "Carlos", "Silva", LocalDate.parse("1990-03-15"), List.of(team));

        PlayerResponse response = PlayerResponseMapper.toResponse(result);

        assertThat(response.teams()).hasSize(1);
        assertThat(response.teams().getFirst().teamId()).isEqualTo(7);
        assertThat(response.teams().getFirst().name()).isEqualTo("Real Madrid");
        assertThat(response.teams().getFirst().sportName()).isEqualTo("Football");
    }

    @Test
    void teamResponseMapper_mapsAllFields() {
        TeamResult result = new TeamResult(7, "Real Madrid", 1, "Football");

        TeamResponse response = TeamResponseMapper.toResponse(result);

        assertThat(response.teamId()).isEqualTo(7);
        assertThat(response.name()).isEqualTo("Real Madrid");
        assertThat(response.sportId()).isEqualTo(1);
        assertThat(response.sportName()).isEqualTo("Football");
    }

    @Test
    void teamResponseMapper_mapsNullSportName() {
        TeamResult result = new TeamResult(7, "Free Agents", null, null);

        TeamResponse response = TeamResponseMapper.toResponse(result);

        assertThat(response.sportId()).isNull();
        assertThat(response.sportName()).isNull();
    }

    @Test
    void sportResponseMapper_mapsAllFields() {
        SportResult result = new SportResult(1, "Football");

        SportResponse response = SportResponseMapper.toResponse(result);

        assertThat(response.sportId()).isEqualTo(1);
        assertThat(response.name()).isEqualTo("Football");
    }

    @Test
    void sportResponseMapper_mapsNullName() {
        SportResult result = new SportResult(1, null);

        SportResponse response = SportResponseMapper.toResponse(result);

        assertThat(response.sportId()).isEqualTo(1);
        assertThat(response.name()).isNull();
    }
}
