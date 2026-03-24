package com.sport_events.api.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sport_events.api.domain.model.SportTranslation;
import com.sport_events.api.domain.repository.SportTranslationRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.SportTranslationJpaRepository;
import com.sport_events.api.infrastructure.persistence.mapper.SportTranslationMapper;

@Repository
public class SportTranslationRepositoryAdapter implements SportTranslationRepository {

    private final SportTranslationJpaRepository jpaRepository;

    public SportTranslationRepositoryAdapter(SportTranslationJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<SportTranslation> findById(Integer sportTranslationId) {
        return jpaRepository.findById(sportTranslationId)
                .map(SportTranslationMapper::toDomain);
    }

    @Override
    public List<SportTranslation> findAll() {
        return jpaRepository.findAll().stream()
                .map(SportTranslationMapper::toDomain)
                .toList();
    }

    @Override
    public List<SportTranslation> findBySportId(Integer sportId) {
        return jpaRepository.findBySport_SportId(sportId).stream()
                .map(SportTranslationMapper::toDomain)
                .toList();
    }

    @Override
    public List<SportTranslation> findByLanguageId(Integer languageId) {
        return jpaRepository.findByLanguage_LanguageId(languageId).stream()
                .map(SportTranslationMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<SportTranslation> findBySportIdAndLanguageId(Integer sportId, Integer languageId) {
        return jpaRepository.findBySport_SportIdAndLanguage_LanguageId(sportId, languageId)
                .map(SportTranslationMapper::toDomain);
    }

    @Override
    public SportTranslation save(SportTranslation sportTranslation) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteById(Integer sportTranslationId) {
        jpaRepository.deleteById(sportTranslationId);
    }
}
