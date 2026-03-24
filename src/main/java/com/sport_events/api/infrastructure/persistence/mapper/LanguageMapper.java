package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.Language;
import com.sport_events.api.infrastructure.persistence.jpa.entity.LanguageJpaEntity;

public class LanguageMapper {

    public static Language toDomain(LanguageJpaEntity entity) {
        return new Language(
                entity.getLanguageId(),
                entity.getCode(),
                entity.getName());
    }
}
