package com.sport_events.api.application.usecase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sport_events.api.application.dto.command.CreateSportCommand;
import com.sport_events.api.application.dto.command.SportTranslationCommand;
import com.sport_events.api.application.dto.result.SportResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Language;
import com.sport_events.api.domain.model.Sport;
import com.sport_events.api.domain.model.SportTranslation;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.SportRepository;
import com.sport_events.api.domain.repository.SportTranslationRepository;

public class CreateSportUseCase {

    private final SportRepository sportRepository;
    private final SportTranslationRepository sportTranslationRepository;
    private final LanguageRepository languageRepository;

    public CreateSportUseCase(
            SportRepository sportRepository,
            SportTranslationRepository sportTranslationRepository,
            LanguageRepository languageRepository) {
        this.sportRepository = sportRepository;
        this.sportTranslationRepository = sportTranslationRepository;
        this.languageRepository = languageRepository;
    }

    public SportResult execute(CreateSportCommand command) {
        List<SportTranslationCommand> translations = command.translations();
        if (translations == null || translations.isEmpty()) {
            throw new DomainException("At least one translation is required");
        }

        Set<String> languageCodes = new HashSet<>();
        for (SportTranslationCommand translation : translations) {
            String languageCode = translation.languageCode();
            if (!languageCodes.add(languageCode)) {
                throw new DomainException("Duplicate language in request: " + languageCode);
            }
        }

        Sport saved = sportRepository.save(new Sport(null));
        String responseName = null;

        for (SportTranslationCommand translationCommand : translations) {
            Language language = languageRepository.findByCode(translationCommand.languageCode())
                    .orElseThrow(() -> new DomainException("Language not found: " + translationCommand.languageCode()));
            SportTranslation translation = new SportTranslation(
                    null, saved.getSportId(), language.getLanguageId(), translationCommand.name());
            SportTranslation savedTranslation = sportTranslationRepository.save(translation);
            if (responseName == null) {
                responseName = savedTranslation.getName();
            }
        }

        return new SportResult(saved.getSportId(), responseName);
    }
}
