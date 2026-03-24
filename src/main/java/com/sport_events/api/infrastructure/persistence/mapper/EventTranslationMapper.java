package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.EventTranslation;
import com.sport_events.api.infrastructure.persistence.jpa.entity.EventTranslationJpaEntity;

public class EventTranslationMapper {

    public static EventTranslation toDomain(EventTranslationJpaEntity entity) {
        return new EventTranslation(
                entity.getEventTranslationId(),
                entity.getEvent() != null ? entity.getEvent().getEventId() : null,
                entity.getLanguage() != null ? entity.getLanguage().getLanguageId() : null,
                entity.getName(),
                entity.getDescription());
    }
}
