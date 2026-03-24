package com.sport_events.api.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sport_events.api.domain.model.Language;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.LanguageJpaRepository;
import com.sport_events.api.infrastructure.persistence.mapper.LanguageMapper;

@Repository
public class LanguageRepositoryAdapter implements LanguageRepository {

    private final LanguageJpaRepository jpaRepository;

    public LanguageRepositoryAdapter(LanguageJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Language> findById(Integer languageId) {
        return jpaRepository.findById(languageId)
                .map(LanguageMapper::toDomain);
    }

    @Override
    public List<Language> findAll() {
        return jpaRepository.findAll().stream()
                .map(LanguageMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Language> findByCode(String code) {
        return jpaRepository.findByCode(code)
                .map(LanguageMapper::toDomain);
    }

    @Override
    public Language save(Language language) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteById(Integer languageId) {
        jpaRepository.deleteById(languageId);
    }
}
