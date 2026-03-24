package com.sport_events.api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.sport_events.api.domain.model.SportTranslation;

public interface SportTranslationRepository {

    Optional<SportTranslation> findById(Integer sportTranslationId);

    List<SportTranslation> findAll();

    List<SportTranslation> findBySportId(Integer sportId);

    List<SportTranslation> findByLanguageId(Integer languageId);

    Optional<SportTranslation> findBySportIdAndLanguageId(Integer sportId, Integer languageId);

    SportTranslation save(SportTranslation sportTranslation);

    void deleteById(Integer sportTranslationId);
}
