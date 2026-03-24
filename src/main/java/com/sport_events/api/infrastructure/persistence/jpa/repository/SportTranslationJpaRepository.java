package com.sport_events.api.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport_events.api.infrastructure.persistence.jpa.entity.SportTranslationJpaEntity;

@Repository
public interface SportTranslationJpaRepository extends JpaRepository<SportTranslationJpaEntity, Integer> {

	List<SportTranslationJpaEntity> findBySport_SportId(Integer sportId);

	List<SportTranslationJpaEntity> findByLanguage_LanguageId(Integer languageId);

	Optional<SportTranslationJpaEntity> findBySport_SportIdAndLanguage_LanguageId(Integer sportId, Integer languageId);
}
