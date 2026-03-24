package com.sport_events.api.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sport_events.api.domain.model.Team;
import com.sport_events.api.domain.repository.TeamRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.TeamJpaRepository;
import com.sport_events.api.infrastructure.persistence.mapper.TeamMapper;

@Repository
public class TeamRepositoryAdapter implements TeamRepository {

    private final TeamJpaRepository jpaRepository;

    public TeamRepositoryAdapter(TeamJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Team> findById(Integer teamId) {
        return jpaRepository.findById(teamId)
                .map(TeamMapper::toDomain);
    }

    @Override
    public List<Team> findAll() {
        return jpaRepository.findAll().stream()
                .map(TeamMapper::toDomain)
                .toList();
    }

    @Override
    public List<Team> findBySportId(Integer sportId) {
        return jpaRepository.findBySport_SportId(sportId).stream()
                .map(TeamMapper::toDomain)
                .toList();
    }

    @Override
    public Team save(Team team) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteById(Integer teamId) {
        jpaRepository.deleteById(teamId);
    }
}
