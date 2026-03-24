package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.SportTranslation;
import com.sport_events.api.infrastructure.persistence.jpa.entity.SportTranslationJpaEntity;

public class SportTranslationMapper {

    public static SportTranslation toDomain(SportTranslationJpaEntity entity) {
        return new SportTranslation(
                entity.getSportTranslationId(),
                entity.getSport() != null ? entity.getSport().getSportId() : null,
                entity.getLanguage() != null ? entity.getLanguage().getLanguageId() : null,
                entity.getName());
    }
}
