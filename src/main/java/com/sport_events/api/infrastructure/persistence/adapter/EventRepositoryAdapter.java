package com.sport_events.api.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.repository.EventRepository;
import com.sport_events.api.infrastructure.persistence.jpa.entity.VenueJpaEntity;
import com.sport_events.api.infrastructure.persistence.jpa.repository.EventJpaRepository;
import com.sport_events.api.infrastructure.persistence.mapper.EventMapper;

@Repository
public class EventRepositoryAdapter implements EventRepository {

    private final EventJpaRepository jpaRepository;

    public EventRepositoryAdapter(EventJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Event> findById(Long eventId) {
        return jpaRepository.findById(eventId)
                .map(EventMapper::toDomain);
    }

    @Override
    public List<Event> findAll() {
        return jpaRepository.findAll().stream()
                .map(EventMapper::toDomain)
                .toList();
    }

    @Override
    public Event save(Event event) {
        VenueJpaEntity venue = new VenueJpaEntity();
        venue.setVenueId(event.getVenueId());
        var entity = EventMapper.toEntity(event, venue);
        return EventMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long eventId) {
        jpaRepository.deleteById(eventId);
    }
}
