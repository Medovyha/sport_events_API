package com.sport_events.api.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport_events.api.infrastructure.persistence.jpa.entity.TeamPlayerJpaEntity;

@Repository
public interface TeamPlayerJpaRepository extends JpaRepository<TeamPlayerJpaEntity, Integer> {

    List<TeamPlayerJpaEntity> findByTeamId(Integer teamId);

    Optional<TeamPlayerJpaEntity> findByTeamIdAndPlayerId(Integer teamId, Integer playerId);
}
