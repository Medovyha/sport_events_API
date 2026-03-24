package com.sport_events.api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.sport_events.api.domain.model.Language;

public interface LanguageRepository {

	Optional<Language> findById(Integer languageId);

	List<Language> findAll();

	Optional<Language> findByCode(String code);

	Language save(Language language);

	void deleteById(Integer languageId);
}
