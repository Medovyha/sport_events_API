package com.sport_events.api.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport_events.api.infrastructure.persistence.jpa.entity.VenueJpaEntity;

@Repository
public interface VenueJpaRepository extends JpaRepository<VenueJpaEntity, Integer> {

}
