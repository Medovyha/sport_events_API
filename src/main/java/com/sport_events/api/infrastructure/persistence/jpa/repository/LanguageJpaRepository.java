package com.sport_events.api.infrastructure.persistence.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport_events.api.infrastructure.persistence.jpa.entity.LanguageJpaEntity;

@Repository
public interface LanguageJpaRepository extends JpaRepository<LanguageJpaEntity, Integer> {

	Optional<LanguageJpaEntity> findByCode(String code);
}
