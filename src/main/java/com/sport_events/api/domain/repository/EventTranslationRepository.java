package com.sport_events.api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.sport_events.api.domain.model.EventTranslation;

public interface EventTranslationRepository {

    Optional<EventTranslation> findById(Integer eventTranslationId);

    List<EventTranslation> findAll();

    List<EventTranslation> findByEventId(Long eventId);

    List<EventTranslation> findByLanguageId(Integer languageId);

    Optional<EventTranslation> findByEventIdAndLanguageId(Long eventId, Integer languageId);

    EventTranslation save(EventTranslation eventTranslation);

    void deleteById(Integer eventTranslationId);
}
