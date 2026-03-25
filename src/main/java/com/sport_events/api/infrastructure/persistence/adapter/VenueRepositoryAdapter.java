package com.sport_events.api.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sport_events.api.domain.model.Venue;
import com.sport_events.api.domain.repository.VenueRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.VenueJpaRepository;
import com.sport_events.api.infrastructure.persistence.mapper.VenueMapper;

@Repository
public class VenueRepositoryAdapter implements VenueRepository {

    private final VenueJpaRepository jpaRepository;

    public VenueRepositoryAdapter(VenueJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Venue> findById(Integer venueId) {
        return jpaRepository.findById(venueId)
                .map(VenueMapper::toDomain);
    }

    @Override
    public List<Venue> findAll() {
        return jpaRepository.findAll().stream()
                .map(VenueMapper::toDomain)
                .toList();
    }

    @Override
    public Venue save(Venue venue) {
        var entity = VenueMapper.toEntity(venue);
        return VenueMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void deleteById(Integer venueId) {
        jpaRepository.deleteById(venueId);
    }
}
