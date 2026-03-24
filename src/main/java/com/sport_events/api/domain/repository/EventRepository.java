package com.sport_events.api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.sport_events.api.domain.model.Event;

public interface EventRepository {

	Optional<Event> findById(Long eventId);

	List<Event> findAll();

	Event save(Event event);

	void deleteById(Long eventId);
}
