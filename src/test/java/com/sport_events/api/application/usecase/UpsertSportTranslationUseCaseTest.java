package com.sport_events.api.application.usecase;

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

import com.sport_events.api.application.dto.command.UpsertSportTranslationCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Language;
import com.sport_events.api.domain.model.Sport;
import com.sport_events.api.domain.model.SportTranslation;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.SportRepository;
import com.sport_events.api.domain.repository.SportTranslationRepository;

@ExtendWith(MockitoExtension.class)
class UpsertSportTranslationUseCaseTest {

    @Mock
    private SportRepository sportRepository;
    @Mock
    private SportTranslationRepository sportTranslationRepository;
    @Mock
    private LanguageRepository languageRepository;

    private UpsertSportTranslationUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpsertSportTranslationUseCase(sportRepository, sportTranslationRepository, languageRepository);
    }

    @Test
    void execute_insertsWhenTranslationDoesNotExist() {
        when(sportRepository.findById(5)).thenReturn(Optional.of(new Sport(5)));
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(new Language(1, "en", "English")));
        when(sportTranslationRepository.findBySportIdAndLanguageId(5, 1)).thenReturn(Optional.empty());
        when(sportTranslationRepository.save(any())).thenReturn(new SportTranslation(10, 5, 1, "Football"));

        var result = useCase.execute(new UpsertSportTranslationCommand(5, "en", "Football"));

        assertThat(result.sportId()).isEqualTo(5);
        assertThat(result.name()).isEqualTo("Football");
    }

    @Test
    void execute_updatesWhenTranslationExists() {
        when(sportRepository.findById(5)).thenReturn(Optional.of(new Sport(5)));
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(new Language(1, "en", "English")));
        when(sportTranslationRepository.findBySportIdAndLanguageId(5, 1))
                .thenReturn(Optional.of(new SportTranslation(10, 5, 1, "Old")));
        when(sportTranslationRepository.save(any())).thenReturn(new SportTranslation(10, 5, 1, "Football"));

        var result = useCase.execute(new UpsertSportTranslationCommand(5, "en", "Football"));

        assertThat(result.sportId()).isEqualTo(5);
        assertThat(result.name()).isEqualTo("Football");
    }

    @Test
    void execute_throwsWhenSportNotFound() {
        when(sportRepository.findById(7)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new UpsertSportTranslationCommand(7, "en", "Football")))
                .isInstanceOf(DomainException.class)
                .hasMessage("Sport not found: 7");
    }

    @Test
    void execute_throwsWhenLanguageNotFound() {
        when(sportRepository.findById(5)).thenReturn(Optional.of(new Sport(5)));
        when(languageRepository.findByCode("zz")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new UpsertSportTranslationCommand(5, "zz", "Football")))
                .isInstanceOf(DomainException.class)
                .hasMessage("Language not found: zz");
    }
}
