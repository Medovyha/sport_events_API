package com.sport_events.api.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport_events.api.infrastructure.persistence.jpa.entity.EventPlayerJpaEntity;

@Repository
public interface EventPlayerJpaRepository extends JpaRepository<EventPlayerJpaEntity, Integer> {

	List<EventPlayerJpaEntity> findByEventTeamId(Integer eventTeamId);

	Optional<EventPlayerJpaEntity> findByEventTeamIdAndPlayerId(Integer eventTeamId, Integer playerId);
}
