package com.sport_events.api.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sport_events.api.domain.model.TeamPlayer;
import com.sport_events.api.domain.repository.TeamPlayerRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.TeamPlayerJpaRepository;
import com.sport_events.api.infrastructure.persistence.mapper.TeamPlayerMapper;

@Repository
public class TeamPlayerRepositoryAdapter implements TeamPlayerRepository {

    private final TeamPlayerJpaRepository jpaRepository;

    public TeamPlayerRepositoryAdapter(TeamPlayerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<TeamPlayer> findById(Integer teamPlayerId) {
        return jpaRepository.findById(teamPlayerId)
                .map(TeamPlayerMapper::toDomain);
    }

    @Override
    public List<TeamPlayer> findAll() {
        return jpaRepository.findAll().stream()
                .map(TeamPlayerMapper::toDomain)
                .toList();
    }

    @Override
    public List<TeamPlayer> findByTeamId(Integer teamId) {
        return jpaRepository.findByTeam_TeamId(teamId).stream()
                .map(TeamPlayerMapper::toDomain)
                .toList();
    }

    @Override
    public List<TeamPlayer> findByPlayerId(Integer playerId) {
        return jpaRepository.findByPlayer_PlayerId(playerId).stream()
                .map(TeamPlayerMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<TeamPlayer> findByTeamIdAndPlayerId(Integer teamId, Integer playerId) {
        return jpaRepository.findByTeam_TeamIdAndPlayer_PlayerId(teamId, playerId)
                .map(TeamPlayerMapper::toDomain);
    }

    @Override
    public TeamPlayer save(TeamPlayer teamPlayer) {
        var entity = TeamPlayerMapper.toEntity(teamPlayer);
        return TeamPlayerMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void deleteById(Integer teamPlayerId) {
        jpaRepository.deleteById(teamPlayerId);
    }
}
