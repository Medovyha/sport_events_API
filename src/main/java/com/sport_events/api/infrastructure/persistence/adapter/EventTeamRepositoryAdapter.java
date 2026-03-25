package com.sport_events.api.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sport_events.api.domain.model.EventTeam;
import com.sport_events.api.domain.repository.EventTeamRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.EventTeamJpaRepository;
import com.sport_events.api.infrastructure.persistence.mapper.EventTeamMapper;

@Repository
public class EventTeamRepositoryAdapter implements EventTeamRepository {

    private final EventTeamJpaRepository jpaRepository;

    public EventTeamRepositoryAdapter(EventTeamJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<EventTeam> findById(Integer eventTeamsId) {
        return jpaRepository.findById(eventTeamsId)
                .map(EventTeamMapper::toDomain);
    }

    @Override
    public List<EventTeam> findAll() {
        return jpaRepository.findAll().stream()
                .map(EventTeamMapper::toDomain)
                .toList();
    }

    @Override
    public List<EventTeam> findByEventId(Long eventId) {
        return jpaRepository.findByEvent_EventId(eventId).stream()
                .map(EventTeamMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<EventTeam> findByEventIdAndTeamId(Long eventId, Integer teamId) {
        return jpaRepository.findByEvent_EventIdAndTeam_TeamId(eventId, teamId)
                .map(EventTeamMapper::toDomain);
    }

    @Override
    public EventTeam save(EventTeam eventTeam) {
        var entity = EventTeamMapper.toEntity(eventTeam);
        return EventTeamMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void deleteById(Integer eventTeamsId) {
        jpaRepository.deleteById(eventTeamsId);
    }
}
