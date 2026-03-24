package com.sport_events.api.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport_events.api.infrastructure.persistence.jpa.entity.EventTeamJpaEntity;

@Repository
public interface EventTeamJpaRepository extends JpaRepository<EventTeamJpaEntity, Integer> {

	List<EventTeamJpaEntity> findByEvent_EventId(Long eventId);

	Optional<EventTeamJpaEntity> findByEvent_EventIdAndTeam_TeamId(Long eventId, Integer teamId);
}
