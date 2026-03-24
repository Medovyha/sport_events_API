package com.sport_events.api.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sport_events.api.domain.model.EventTranslation;
import com.sport_events.api.domain.repository.EventTranslationRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.EventTranslationJpaRepository;
import com.sport_events.api.infrastructure.persistence.mapper.EventTranslationMapper;

@Repository
public class EventTranslationRepositoryAdapter implements EventTranslationRepository {

    private final EventTranslationJpaRepository jpaRepository;

    public EventTranslationRepositoryAdapter(EventTranslationJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<EventTranslation> findById(Integer eventTranslationId) {
        return jpaRepository.findById(eventTranslationId)
                .map(EventTranslationMapper::toDomain);
    }

    @Override
    public List<EventTranslation> findAll() {
        return jpaRepository.findAll().stream()
                .map(EventTranslationMapper::toDomain)
                .toList();
    }

    @Override
    public List<EventTranslation> findByEventId(Long eventId) {
        return jpaRepository.findByEvent_EventId(eventId).stream()
                .map(EventTranslationMapper::toDomain)
                .toList();
    }

    @Override
    public List<EventTranslation> findByLanguageId(Integer languageId) {
        return jpaRepository.findByLanguage_LanguageId(languageId).stream()
                .map(EventTranslationMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<EventTranslation> findByEventIdAndLanguageId(Long eventId, Integer languageId) {
        return jpaRepository.findByEvent_EventIdAndLanguage_LanguageId(eventId, languageId)
                .map(EventTranslationMapper::toDomain);
    }

    @Override
    public EventTranslation save(EventTranslation eventTranslation) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteById(Integer eventTranslationId) {
        jpaRepository.deleteById(eventTranslationId);
    }
}
