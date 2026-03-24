package com.sport_events.api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.sport_events.api.domain.model.Sport;

public interface SportRepository {

	Optional<Sport> findById(Integer sportId);

	List<Sport> findAll();

	Sport save(Sport sport);

	void deleteById(Integer sportId);
}
