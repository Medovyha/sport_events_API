package com.sport_events.api.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sport_events.api.domain.model.Sport;
import com.sport_events.api.domain.repository.SportRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.SportJpaRepository;
import com.sport_events.api.infrastructure.persistence.mapper.SportMapper;

@Repository
public class SportRepositoryAdapter implements SportRepository {

    private final SportJpaRepository jpaRepository;

    public SportRepositoryAdapter(SportJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Sport> findById(Integer sportId) {
        return jpaRepository.findById(sportId)
                .map(SportMapper::toDomain);
    }

    @Override
    public List<Sport> findAll() {
        return jpaRepository.findAll().stream()
                .map(SportMapper::toDomain)
                .toList();
    }

    @Override
    public Sport save(Sport sport) {
        var entity = SportMapper.toEntity(sport);
        return SportMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void deleteById(Integer sportId) {
        jpaRepository.deleteById(sportId);
    }
}
