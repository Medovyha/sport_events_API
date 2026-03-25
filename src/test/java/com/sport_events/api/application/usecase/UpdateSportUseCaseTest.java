package com.sport_events.api.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.application.dto.command.SportTranslationCommand;
import com.sport_events.api.application.dto.command.UpdateSportCommand;
import com.sport_events.api.application.dto.result.SportResult;
import com.sport_events.api.domain.exception.DomainException;

@ExtendWith(MockitoExtension.class)
class UpdateSportUseCaseTest {

    @Mock
    private UpsertSportTranslationUseCase upsertSportTranslationUseCase;

    private UpdateSportUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateSportUseCase(upsertSportTranslationUseCase);
    }

    @Test
    void execute_upsertsAllTranslations() {
        when(upsertSportTranslationUseCase.execute(any())).thenReturn(new SportResult(5, "Football"));

        var result = useCase.execute(new UpdateSportCommand(5, java.util.List.of(
                new SportTranslationCommand("en", "Football"),
                new SportTranslationCommand("uk", "Футбол"))));

        assertThat(result.sportId()).isEqualTo(5);
        assertThat(result.name()).isEqualTo("Football");
    }

    @Test
    void execute_throwsWhenNoTranslations() {
        assertThatThrownBy(() -> useCase.execute(new UpdateSportCommand(5, java.util.List.of())))
                .isInstanceOf(DomainException.class)
                .hasMessage("At least one translation is required");
    }

    @Test
    void execute_throwsWhenDuplicateLanguagesProvided() {
        assertThatThrownBy(() -> useCase.execute(new UpdateSportCommand(5, java.util.List.of(
                new SportTranslationCommand("en", "Football"),
                new SportTranslationCommand("en", "Soccer")))))
                .isInstanceOf(DomainException.class)
                .hasMessage("Duplicate language in request: en");
    }
}
