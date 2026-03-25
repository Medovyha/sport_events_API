package com.sport_events.api.application.usecase;

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

import com.sport_events.api.application.dto.result.TeamResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Language;
import com.sport_events.api.domain.model.SportTranslation;
import com.sport_events.api.domain.model.Team;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.SportTranslationRepository;
import com.sport_events.api.domain.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class GetTeamUseCaseTest {

    @Mock
    private TeamRepository teamRepository;
    @Mock
    private LanguageRepository languageRepository;
    @Mock
    private SportTranslationRepository sportTranslationRepository;

    private GetTeamUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetTeamUseCase(teamRepository, languageRepository, sportTranslationRepository);
    }

    @Test
    void findById_returnsTeamWithLocalizedSportName() {
        Language english = new Language(1, "en", "English");
        Team team = new Team(7, "Real Madrid", 1);
        SportTranslation st = new SportTranslation(31, 1, 1, "Football");

        when(teamRepository.findById(7)).thenReturn(Optional.of(team));
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));
        when(sportTranslationRepository.findBySportIdAndLanguageId(1, 1)).thenReturn(Optional.of(st));

        TeamResult result = useCase.findById(7, "en");

        assertThat(result.teamId()).isEqualTo(7);
        assertThat(result.name()).isEqualTo("Real Madrid");
        assertThat(result.sportId()).isEqualTo(1);
        assertThat(result.sportName()).isEqualTo("Football");
    }

    @Test
    void findById_throwsWhenTeamNotFound() {
        when(teamRepository.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.findById(999, "en"))
                .isInstanceOf(DomainException.class)
                .hasMessage("Team not found: 999");
    }

    @Test
    void findById_fallsBackToEnglishSportName() {
        Language polish = new Language(3, "pl", "Polish");
        Language english = new Language(1, "en", "English");
        Team team = new Team(7, "Real Madrid", 1);
        SportTranslation englishSt = new SportTranslation(32, 1, 1, "Football");

        when(teamRepository.findById(7)).thenReturn(Optional.of(team));
        when(languageRepository.findByCode("pl")).thenReturn(Optional.of(polish));
        when(sportTranslationRepository.findBySportIdAndLanguageId(1, 3)).thenReturn(Optional.empty());
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));
        when(sportTranslationRepository.findBySportIdAndLanguageId(1, 1)).thenReturn(Optional.of(englishSt));

        TeamResult result = useCase.findById(7, "pl");

        assertThat(result.sportName()).isEqualTo("Football");
    }

    @Test
    void findById_returnsNullSportNameWhenSportIdIsNull() {
        Language english = new Language(1, "en", "English");
        Team team = new Team(7, "Free Agents", null);

        when(teamRepository.findById(7)).thenReturn(Optional.of(team));
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));

        TeamResult result = useCase.findById(7, "en");

        assertThat(result.sportName()).isNull();
    }

    @Test
    void findAll_returnsTeamsWithSportName() {
        Language english = new Language(1, "en", "English");
        SportTranslation st = new SportTranslation(31, 1, 1, "Football");

        when(teamRepository.findAll()).thenReturn(List.of(
                new Team(7, "Real Madrid", 1),
                new Team(8, "Barcelona", 1)));
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));
        when(sportTranslationRepository.findBySportIdAndLanguageId(1, 1)).thenReturn(Optional.of(st));

        List<TeamResult> results = useCase.findAll("en");

        assertThat(results).hasSize(2);
        assertThat(results.get(0).sportName()).isEqualTo("Football");
        assertThat(results.get(1).sportName()).isEqualTo("Football");
    }

    @Test
    void findBySportId_returnsOnlyTeamsForThatSport() {
        Language english = new Language(1, "en", "English");
        SportTranslation st = new SportTranslation(31, 1, 1, "Football");

        when(teamRepository.findBySportId(1)).thenReturn(List.of(new Team(7, "Real Madrid", 1)));
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));
        when(sportTranslationRepository.findBySportIdAndLanguageId(1, 1)).thenReturn(Optional.of(st));

        List<TeamResult> results = useCase.findBySportId(1, "en");

        assertThat(results).hasSize(1);
        assertThat(results.getFirst().name()).isEqualTo("Real Madrid");
        assertThat(results.getFirst().sportName()).isEqualTo("Football");
    }

    @Test
    void findById_returnsNullSportNameWhenNoTranslationExists() {
        Language english = new Language(1, "en", "English");
        Team team = new Team(7, "Real Madrid", 1);

        when(teamRepository.findById(7)).thenReturn(Optional.of(team));
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));
        when(sportTranslationRepository.findBySportIdAndLanguageId(1, 1)).thenReturn(Optional.empty());

        TeamResult result = useCase.findById(7, "en");

        assertThat(result.sportName()).isNull();
    }
}
