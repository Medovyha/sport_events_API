package com.sport_events.api.infrastructure.persistence.jpa.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport_events.api.infrastructure.persistence.jpa.entity.EventJpaEntity;

@Repository
public interface EventJpaRepository extends JpaRepository<EventJpaEntity, Long> {

	List<EventJpaEntity> findByVenueId(Integer venueId);

	List<EventJpaEntity> findByStartsAtBetween(OffsetDateTime from, OffsetDateTime to);
}
