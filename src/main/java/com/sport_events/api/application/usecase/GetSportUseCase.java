package com.sport_events.api.application.usecase;

import java.util.List;

import com.sport_events.api.application.dto.result.SportResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Language;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.SportRepository;
import com.sport_events.api.domain.repository.SportTranslationRepository;

public class GetSportUseCase {

    private final SportRepository sportRepository;
    private final SportTranslationRepository sportTranslationRepository;
    private final LanguageRepository languageRepository;

    public GetSportUseCase(
            SportRepository sportRepository,
            SportTranslationRepository sportTranslationRepository,
            LanguageRepository languageRepository) {
        this.sportRepository = sportRepository;
        this.sportTranslationRepository = sportTranslationRepository;
        this.languageRepository = languageRepository;
    }

    public SportResult findById(Integer sportId, String languageCode) {
        sportRepository.findById(sportId)
                .orElseThrow(() -> new DomainException("Sport not found: " + sportId));
        Integer languageId = resolveLanguageId(languageCode);
        String name = resolveSportName(sportId, languageId);
        return new SportResult(sportId, name);
    }

    public List<SportResult> findAll(String languageCode) {
        Integer languageId = resolveLanguageId(languageCode);
        return sportRepository.findAll().stream()
                .map(s -> new SportResult(s.getSportId(), resolveSportName(s.getSportId(), languageId)))
                .toList();
    }

    private Integer resolveLanguageId(String languageCode) {
        Language language = languageRepository.findByCode(languageCode)
                .or(() -> languageRepository.findByCode("en"))
                .orElse(null);
        return language != null ? language.getLanguageId() : null;
    }

    private String resolveSportName(Integer sportId, Integer languageId) {
        if (languageId != null) {
            java.util.Optional<String> localized = sportTranslationRepository
                    .findBySportIdAndLanguageId(sportId, languageId)
                    .map(st -> st.getName());
            if (localized.isPresent()) {
                return localized.get();
            }
        }
        return languageRepository.findByCode("en")
                .flatMap(en -> sportTranslationRepository.findBySportIdAndLanguageId(sportId, en.getLanguageId()))
                .map(st -> st.getName())
                .orElse(null);
    }
}
