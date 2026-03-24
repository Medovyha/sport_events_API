package com.sport_events.api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.sport_events.api.domain.model.EventTeam;

public interface EventTeamRepository {

    Optional<EventTeam> findById(Integer eventTeamsId);

    List<EventTeam> findAll();

    List<EventTeam> findByEventId(Long eventId);

    Optional<EventTeam> findByEventIdAndTeamId(Long eventId, Integer teamId);

    EventTeam save(EventTeam eventTeam);

    void deleteById(Integer eventTeamsId);
}
