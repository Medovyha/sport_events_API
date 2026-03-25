package com.sport_events.api.application.usecase;

import com.sport_events.api.application.dto.command.UpsertSportTranslationCommand;
import com.sport_events.api.application.dto.result.SportResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Language;
import com.sport_events.api.domain.model.SportTranslation;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.SportRepository;
import com.sport_events.api.domain.repository.SportTranslationRepository;

public class UpsertSportTranslationUseCase {

    private final SportRepository sportRepository;
    private final SportTranslationRepository sportTranslationRepository;
    private final LanguageRepository languageRepository;

    public UpsertSportTranslationUseCase(
            SportRepository sportRepository,
            SportTranslationRepository sportTranslationRepository,
            LanguageRepository languageRepository) {
        this.sportRepository = sportRepository;
        this.sportTranslationRepository = sportTranslationRepository;
        this.languageRepository = languageRepository;
    }

    public SportResult execute(UpsertSportTranslationCommand command) {
        sportRepository.findById(command.sportId())
                .orElseThrow(() -> new DomainException("Sport not found: " + command.sportId()));
        Language language = languageRepository.findByCode(command.languageCode())
                .orElseThrow(() -> new DomainException("Language not found: " + command.languageCode()));

        Integer existingId = sportTranslationRepository
                .findBySportIdAndLanguageId(command.sportId(), language.getLanguageId())
                .map(SportTranslation::getSportTranslationId)
                .orElse(null);

        SportTranslation upsert = new SportTranslation(
                existingId,
                command.sportId(),
                language.getLanguageId(),
                command.name());
        SportTranslation saved = sportTranslationRepository.save(upsert);
        return new SportResult(saved.getSportId(), saved.getName());
    }
}
