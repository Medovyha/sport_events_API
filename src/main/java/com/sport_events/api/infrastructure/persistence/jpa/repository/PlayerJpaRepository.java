package com.sport_events.api.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport_events.api.infrastructure.persistence.jpa.entity.PlayerJpaEntity;

@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerJpaEntity, Integer> {

}
