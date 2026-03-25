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

import com.sport_events.api.application.dto.command.CreateSportCommand;
import com.sport_events.api.application.dto.result.SportResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Language;
import com.sport_events.api.domain.model.Sport;
import com.sport_events.api.domain.model.SportTranslation;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.SportRepository;
import com.sport_events.api.domain.repository.SportTranslationRepository;

@ExtendWith(MockitoExtension.class)
class CreateSportUseCaseTest {

    @Mock
    private SportRepository sportRepository;
    @Mock
    private SportTranslationRepository sportTranslationRepository;
    @Mock
    private LanguageRepository languageRepository;

    private CreateSportUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateSportUseCase(sportRepository, sportTranslationRepository, languageRepository);
    }

    @Test
    void execute_createsSportWithTranslationAndReturnsResult() {
        when(languageRepository.findByCode("en")).thenReturn(Optional.of(new Language(1, "en", "English")));
        when(sportRepository.save(any())).thenReturn(new Sport(5));
        SportTranslation savedTrans = new SportTranslation(1, 5, 1, "Football");
        when(sportTranslationRepository.save(any())).thenReturn(savedTrans);

        SportResult result = useCase.execute(new CreateSportCommand("Football", "en"));

        assertThat(result.sportId()).isEqualTo(5);
        assertThat(result.name()).isEqualTo("Football");
    }

    @Test
    void execute_throwsWhenLanguageNotFound() {
        when(languageRepository.findByCode("xx")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new CreateSportCommand("Something", "xx")))
                .isInstanceOf(DomainException.class)
                .hasMessage("Language not found: xx");
    }
}
