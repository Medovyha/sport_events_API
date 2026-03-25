package com.sport_events.api.application.usecase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.application.dto.result.PlayerResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Language;
import com.sport_events.api.domain.model.Player;
import com.sport_events.api.domain.model.SportTranslation;
import com.sport_events.api.domain.model.Team;
import com.sport_events.api.domain.model.TeamPlayer;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.PlayerRepository;
import com.sport_events.api.domain.repository.SportTranslationRepository;
import com.sport_events.api.domain.repository.TeamPlayerRepository;
import com.sport_events.api.domain.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class GetPlayerUseCaseTest {

    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private TeamPlayerRepository teamPlayerRepository;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private LanguageRepository languageRepository;
    @Mock
    private SportTranslationRepository sportTranslationRepository;

    private GetPlayerUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetPlayerUseCase(playerRepository, teamPlayerRepository,
                teamRepository, languageRepository, sportTranslationRepository);
    }

    @Test
    void findById_returnsPlayerResult() {
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(new Language(1, "en", "English")));
        when(playerRepository.findById(100)).thenReturn(
                Optional.of(new Player(100, "Carlos", "Silva", LocalDate.parse("1990-03-15"))));
        when(teamPlayerRepository.findByPlayerId(100)).thenReturn(List.of());

        PlayerResult result = useCase.findById(100, "en");

        assertThat(result.playerId()).isEqualTo(100);
        assertThat(result.firstName()).isEqualTo("Carlos");
        assertThat(result.lastName()).isEqualTo("Silva");
        assertThat(result.dateOfBirth()).isEqualTo(LocalDate.parse("1990-03-15"));
        assertThat(result.teams()).isEmpty();
    }

    @Test
    void findById_includesPlayerTeams() {
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(new Language(1, "en", "English")));
        when(playerRepository.findById(100)).thenReturn(
                Optional.of(new Player(100, "Carlos", "Silva", LocalDate.parse("1990-03-15"))));
        when(teamPlayerRepository.findByPlayerId(100)).thenReturn(List.of(new TeamPlayer(1, 7, 100)));
        when(teamRepository.findById(7)).thenReturn(Optional.of(new Team(7, "Real Madrid", 5)));
        when(sportTranslationRepository.findBySportIdAndLanguageId(5, 1))
                .thenReturn(Optional.of(new SportTranslation(1, 5, 1, "Football")));

        PlayerResult result = useCase.findById(100, "en");

        assertThat(result.teams()).hasSize(1);
        assertThat(result.teams().getFirst().teamId()).isEqualTo(7);
        assertThat(result.teams().getFirst().name()).isEqualTo("Real Madrid");
        assertThat(result.teams().getFirst().sportName()).isEqualTo("Football");
    }

    @Test
    void findById_throwsWhenPlayerNotFound() {
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(new Language(1, "en", "English")));
        when(playerRepository.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.findById(999, "en"))
                .isInstanceOf(DomainException.class)
                .hasMessage("Player not found: 999");
    }

    @Test
    void findAll_returnsAllPlayers() {
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(new Language(1, "en", "English")));
        when(playerRepository.findAll()).thenReturn(List.of(
                new Player(1, "Alice", "Smith", LocalDate.parse("1995-06-20")),
                new Player(2, "Bob", "Jones", LocalDate.parse("1992-11-11"))));
        when(teamPlayerRepository.findByPlayerId(1)).thenReturn(List.of());
        when(teamPlayerRepository.findByPlayerId(2)).thenReturn(List.of());

        List<PlayerResult> results = useCase.findAll("en");

        assertThat(results).hasSize(2);
        assertThat(results.get(0).firstName()).isEqualTo("Alice");
        assertThat(results.get(1).firstName()).isEqualTo("Bob");
    }

    @Test
    void findByTeamId_returnsPlayersViaTeamPlayerTable() {
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(new Language(1, "en", "English")));
        Player player = new Player(100, "Carlos", "Silva", LocalDate.parse("1990-03-15"));
        when(teamPlayerRepository.findByTeamId(7)).thenReturn(List.of(new TeamPlayer(1, 7, 100)));
        when(playerRepository.findById(100)).thenReturn(Optional.of(player));
        when(teamPlayerRepository.findByPlayerId(100)).thenReturn(List.of());

        List<PlayerResult> results = useCase.findByTeamId(7, "en");

        assertThat(results).hasSize(1);
        assertThat(results.getFirst().firstName()).isEqualTo("Carlos");
    }

    @Test
    void findByTeamId_throwsWhenLinkedPlayerMissing() {
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(new Language(1, "en", "English")));
        when(teamPlayerRepository.findByTeamId(7)).thenReturn(List.of(new TeamPlayer(1, 7, 999)));
        when(playerRepository.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.findByTeamId(7, "en"))
                .isInstanceOf(DomainException.class)
                .hasMessage("Player not found: 999");
    }

    @Test
    void findByTeamId_returnsEmptyListWhenTeamHasNoPlayers() {
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(new Language(1, "en", "English")));
        when(teamPlayerRepository.findByTeamId(7)).thenReturn(List.of());

        assertThat(useCase.findByTeamId(7, "en")).isEmpty();
    }
}

