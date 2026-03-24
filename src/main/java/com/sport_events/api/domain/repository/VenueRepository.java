package com.sport_events.api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.sport_events.api.domain.model.Venue;

public interface VenueRepository {

	Optional<Venue> findById(Integer venueId);

	List<Venue> findAll();

	Venue save(Venue venue);

	void deleteById(Integer venueId);
}
