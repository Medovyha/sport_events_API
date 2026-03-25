package com.sport_events.api.application.usecase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sport_events.api.application.dto.command.SportTranslationCommand;
import com.sport_events.api.application.dto.command.UpdateSportCommand;
import com.sport_events.api.application.dto.command.UpsertSportTranslationCommand;
import com.sport_events.api.application.dto.result.SportResult;
import com.sport_events.api.domain.exception.DomainException;

public class UpdateSportUseCase {

    private final UpsertSportTranslationUseCase upsertSportTranslationUseCase;

    public UpdateSportUseCase(UpsertSportTranslationUseCase upsertSportTranslationUseCase) {
        this.upsertSportTranslationUseCase = upsertSportTranslationUseCase;
    }

    public SportResult execute(UpdateSportCommand command) {
        List<SportTranslationCommand> translations = command.translations();
        if (translations == null || translations.isEmpty()) {
            throw new DomainException("At least one translation is required");
        }

        Set<String> languageCodes = new HashSet<>();
        SportResult result = null;
        for (SportTranslationCommand translation : translations) {
            String languageCode = translation.languageCode();
            if (!languageCodes.add(languageCode)) {
                throw new DomainException("Duplicate language in request: " + languageCode);
            }
            result = upsertSportTranslationUseCase.execute(
                    new UpsertSportTranslationCommand(command.sportId(), languageCode, translation.name()));
        }

        return result;
    }
}
