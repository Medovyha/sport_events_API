package com.sport_events.api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.sport_events.api.domain.model.Team;

public interface TeamRepository {

	Optional<Team> findById(Integer teamId);

	List<Team> findAll();

	List<Team> findBySportId(Integer sportId);

	Team save(Team team);

	void deleteById(Integer teamId);
}
