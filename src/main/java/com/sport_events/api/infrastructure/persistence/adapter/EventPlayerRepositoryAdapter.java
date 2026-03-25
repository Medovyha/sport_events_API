package com.sport_events.api.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sport_events.api.domain.model.EventPlayer;
import com.sport_events.api.domain.repository.EventPlayerRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.EventPlayerJpaRepository;
import com.sport_events.api.infrastructure.persistence.mapper.EventPlayerMapper;

@Repository
public class EventPlayerRepositoryAdapter implements EventPlayerRepository {

    private final EventPlayerJpaRepository jpaRepository;

    public EventPlayerRepositoryAdapter(EventPlayerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<EventPlayer> findById(Integer eventPlayersId) {
        return jpaRepository.findById(eventPlayersId)
                .map(EventPlayerMapper::toDomain);
    }

    @Override
    public List<EventPlayer> findAll() {
        return jpaRepository.findAll().stream()
                .map(EventPlayerMapper::toDomain)
                .toList();
    }

    @Override
    public List<EventPlayer> findByEventTeamId(Integer eventTeamId) {
        return jpaRepository.findByEventTeam_EventTeamsId(eventTeamId).stream()
                .map(EventPlayerMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<EventPlayer> findByEventTeamIdAndPlayerId(Integer eventTeamId, Integer playerId) {
        return jpaRepository.findByEventTeam_EventTeamsIdAndPlayer_PlayerId(eventTeamId, playerId)
                .map(EventPlayerMapper::toDomain);
    }

    @Override
    public EventPlayer save(EventPlayer eventPlayer) {
        var entity = EventPlayerMapper.toEntity(eventPlayer);
        return EventPlayerMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void deleteById(Integer eventPlayersId) {
        jpaRepository.deleteById(eventPlayersId);
    }
}
