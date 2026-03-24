package com.sport_events.api.infrastructure.persistence.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport_events.api.infrastructure.persistence.jpa.entity.TeamJpaEntity;

@Repository
public interface TeamJpaRepository extends JpaRepository<TeamJpaEntity, Integer> {

	List<TeamJpaEntity> findBySport_SportId(Integer sportId);
}
