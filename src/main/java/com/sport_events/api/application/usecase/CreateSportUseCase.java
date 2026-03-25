package com.sport_events.api.application.usecase;

import com.sport_events.api.application.dto.command.CreateSportCommand;
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
        Language language = languageRepository.findByCode(command.languageCode())
                .orElseThrow(() -> new DomainException("Language not found: " + command.languageCode()));
        Sport saved = sportRepository.save(new Sport(null));
        SportTranslation translation = new SportTranslation(
                null, saved.getSportId(), language.getLanguageId(), command.name());
        SportTranslation savedTranslation = sportTranslationRepository.save(translation);
        return new SportResult(saved.getSportId(), savedTranslation.getName());
    }
}
