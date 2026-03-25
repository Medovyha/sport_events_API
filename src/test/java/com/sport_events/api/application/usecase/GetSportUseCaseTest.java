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

import com.sport_events.api.application.dto.result.SportResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Language;
import com.sport_events.api.domain.model.Sport;
import com.sport_events.api.domain.model.SportTranslation;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.SportRepository;
import com.sport_events.api.domain.repository.SportTranslationRepository;

@ExtendWith(MockitoExtension.class)
class GetSportUseCaseTest {

    @Mock
    private SportRepository sportRepository;
    @Mock
    private SportTranslationRepository sportTranslationRepository;
    @Mock
    private LanguageRepository languageRepository;

    private GetSportUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetSportUseCase(sportRepository, sportTranslationRepository, languageRepository);
    }

    @Test
    void findById_returnsLocalizedSportName() {
        Language english = new Language(1, "en", "English");
        SportTranslation st = new SportTranslation(31, 1, 1, "Football");

        when(sportRepository.findById(1)).thenReturn(Optional.of(new Sport(1)));
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));
        when(sportTranslationRepository.findBySportIdAndLanguageId(1, 1)).thenReturn(Optional.of(st));

        SportResult result = useCase.findById(1, "en");

        assertThat(result.sportId()).isEqualTo(1);
        assertThat(result.name()).isEqualTo("Football");
    }

    @Test
    void findById_throwsWhenSportNotFound() {
        when(sportRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.findById(99, "en"))
                .isInstanceOf(DomainException.class)
                .hasMessage("Sport not found: 99");
    }

    @Test
    void findById_fallsBackToEnglishNameWhenLocalizedMissing() {
        Language polish = new Language(3, "pl", "Polish");
        Language english = new Language(1, "en", "English");
        SportTranslation englishSt = new SportTranslation(32, 1, 1, "Football");

        when(sportRepository.findById(1)).thenReturn(Optional.of(new Sport(1)));
        when(languageRepository.findByCode("pl")).thenReturn(Optional.of(polish));
        when(sportTranslationRepository.findBySportIdAndLanguageId(1, 3)).thenReturn(Optional.empty());
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));
        when(sportTranslationRepository.findBySportIdAndLanguageId(1, 1)).thenReturn(Optional.of(englishSt));

        SportResult result = useCase.findById(1, "pl");

        assertThat(result.name()).isEqualTo("Football");
    }

    @Test
    void findById_returnsNullNameWhenNoTranslationExists() {
        Language english = new Language(1, "en", "English");

        when(sportRepository.findById(1)).thenReturn(Optional.of(new Sport(1)));
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));
        when(sportTranslationRepository.findBySportIdAndLanguageId(1, 1)).thenReturn(Optional.empty());

        SportResult result = useCase.findById(1, "en");

        assertThat(result.name()).isNull();
    }

    @Test
    void findAll_returnsAllSportsWithNames() {
        Language english = new Language(1, "en", "English");
        SportTranslation st1 = new SportTranslation(31, 1, 1, "Football");
        SportTranslation st2 = new SportTranslation(32, 2, 1, "Basketball");

        when(sportRepository.findAll()).thenReturn(List.of(new Sport(1), new Sport(2)));
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(english));
        when(sportTranslationRepository.findBySportIdAndLanguageId(1, 1)).thenReturn(Optional.of(st1));
        when(sportTranslationRepository.findBySportIdAndLanguageId(2, 1)).thenReturn(Optional.of(st2));

        List<SportResult> results = useCase.findAll("en");

        assertThat(results).hasSize(2);
        assertThat(results.get(0).name()).isEqualTo("Football");
        assertThat(results.get(1).name()).isEqualTo("Basketball");
    }
}
