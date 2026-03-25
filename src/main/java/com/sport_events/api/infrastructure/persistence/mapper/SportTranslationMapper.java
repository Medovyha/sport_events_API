package com.sport_events.api.infrastructure.persistence.mapper;

import com.sport_events.api.domain.model.SportTranslation;
import com.sport_events.api.infrastructure.persistence.jpa.entity.LanguageJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.SportJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.entity.SportTranslationJpaEntity;

public class SportTranslationMapper {

    public static SportTranslation toDomain(SportTranslationJpaEntity entity) {
        return new SportTranslation(
                entity.getSportTranslationId(),
                entity.getSport() != null ? entity.getSport().getSportId() : null,
                entity.getLanguage() != null ? entity.getLanguage().getLanguageId() : null,
                entity.getName());
    }

    public static SportTranslationJpaEntity toEntity(SportTranslation st) {
        SportTranslationJpaEntity entity = new SportTranslationJpaEntity();
        entity.setName(st.getName());
        if (st.getSportId() != null) {
            SportJpaEntity sport = new SportJpaEntity();
            sport.setSportId(st.getSportId());
            entity.setSport(sport);
        }
        if (st.getLanguageId() != null) {
            LanguageJpaEntity language = new LanguageJpaEntity();
            language.setLanguageId(st.getLanguageId());
            entity.setLanguage(language);
        }
        return entity;
    }
}
