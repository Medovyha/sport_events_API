package com.sport_events.api.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport_events.api.infrastructure.persistence.jpa.entity.SportTranslationJpaEntity;

@Repository
public interface SportTranslationJpaRepository extends JpaRepository<SportTranslationJpaEntity, Integer> {

	List<SportTranslationJpaEntity> findBySportId(Integer sportId);

	List<SportTranslationJpaEntity> findByLanguageId(Integer languageId);

	Optional<SportTranslationJpaEntity> findBySportIdAndLanguageId(Integer sportId, Integer languageId);
}
