package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.EventTranslation;
import com.sport_events.api.infrastructure.persistence.jpa.entity.EventJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.EventTranslationJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.LanguageJpaEntity;

public class EventTranslationMapper {

    public static EventTranslation toDomain(EventTranslationJpaEntity entity) {
        return new EventTranslation(
                entity.getEventTranslationId(),
                entity.getEvent() != null ? entity.getEvent().getEventId() : null,
                entity.getLanguage() != null ? entity.getLanguage().getLanguageId() : null,
                entity.getName(),
                entity.getDescription());
    }

    public static EventTranslationJpaEntity toEntity(EventTranslation domain) {
        EventTranslationJpaEntity entity = new EventTranslationJpaEntity();
        entity.setEventTranslationId(domain.getEventTranslationId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());

        EventJpaEntity event = new EventJpaEntity();
        event.setEventId(domain.getEventId());
        entity.setEvent(event);

        LanguageJpaEntity language = new LanguageJpaEntity();
        language.setLanguageId(domain.getLanguageId());
        entity.setLanguage(language);

        return entity;
    }
}
