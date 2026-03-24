package com.sport_events.api.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport_events.api.infrastructure.persistence.jpa.entity.EventTranslationJpaEntity;

@Repository
public interface EventTranslationJpaRepository extends JpaRepository<EventTranslationJpaEntity, Integer> {

	List<EventTranslationJpaEntity> findByEventId(Long eventId);

	List<EventTranslationJpaEntity> findByLanguageId(Integer languageId);

	Optional<EventTranslationJpaEntity> findByEventIdAndLanguageId(Long eventId, Integer languageId);
}
