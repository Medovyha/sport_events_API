package com.sport_events.api.application.usecase;

import java.util.List;

import com.sport_events.api.application.dto.query.GetEventQuery;
import com.sport_events.api.application.dto.result.EventResult;
import com.sport_events.api.application.dto.result.EventTeamResult;
import com.sport_events.api.application.dto.result.EventTranslationResult;
import com.sport_events.api.application.dto.result.PlayerResult;
import com.sport_events.api.application.dto.result.VenueResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.model.Language;
import com.sport_events.api.domain.model.Team;
import com.sport_events.api.domain.model.Venue;
import com.sport_events.api.domain.repository.EventPlayerRepository;
import com.sport_events.api.domain.repository.EventRepository;
import com.sport_events.api.domain.repository.EventTeamRepository;
import com.sport_events.api.domain.repository.EventTranslationRepository;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.PlayerRepository;
import com.sport_events.api.domain.repository.SportTranslationRepository;
import com.sport_events.api.domain.repository.TeamRepository;
import com.sport_events.api.domain.repository.VenueRepository;

public class GetEventUseCase {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final EventTeamRepository eventTeamRepository;
    private final EventTranslationRepository eventTranslationRepository;
    private final EventPlayerRepository eventPlayerRepository;
    private final PlayerRepository playerRepository;
        private final LanguageRepository languageRepository;
        private final TeamRepository teamRepository;
        private final SportTranslationRepository sportTranslationRepository;

    public GetEventUseCase(
            EventRepository eventRepository,
            VenueRepository venueRepository,
            EventTeamRepository eventTeamRepository,
            EventTranslationRepository eventTranslationRepository,
            EventPlayerRepository eventPlayerRepository,
                        PlayerRepository playerRepository,
                        LanguageRepository languageRepository,
                        TeamRepository teamRepository,
                        SportTranslationRepository sportTranslationRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.eventTeamRepository = eventTeamRepository;
        this.eventTranslationRepository = eventTranslationRepository;
        this.eventPlayerRepository = eventPlayerRepository;
        this.playerRepository = playerRepository;
                this.languageRepository = languageRepository;
                this.teamRepository = teamRepository;
                this.sportTranslationRepository = sportTranslationRepository;
    }

    public EventResult execute(GetEventQuery query) {
        Event event = eventRepository.findById(query.eventId())
                .orElseThrow(() -> new DomainException("Event not found: " + query.eventId()));

        Venue venue = venueRepository.findById(event.getVenueId())
                .orElseThrow(() -> new DomainException("Venue not found: " + event.getVenueId()));

        Integer languageId = resolveLanguageId(query.languageCode());

        List<EventTeamResult> teams = eventTeamRepository.findByEventId(event.getEventId())
                .stream()
                .map(et -> {
                    Team team = teamRepository.findById(et.getTeamId())
                            .orElseThrow(() -> new DomainException("Team not found: " + et.getTeamId()));

                    String sportName = resolveSportName(team.getSportId(), languageId);
                    List<PlayerResult> players = eventPlayerRepository.findByEventTeamId(et.getEventTeamsId())
                            .stream()
                            .map(ep -> playerRepository.findById(ep.getPlayerId())
                                    .map(p -> new PlayerResult(
                                            p.getPlayerId(),
                                            p.getFirstName(),
                                            p.getLastName(),
                                            p.getDateOfBirth(),
                                            null))
                                    .orElseThrow(() -> new DomainException(
                                            "Player not found: " + ep.getPlayerId())))
                            .toList();

                    return new EventTeamResult(
                            et.getEventTeamsId(),
                            team.getTeamId(),
                            team.getName(),
                            team.getSportId(),
                            sportName,
                            players);
                })
                .toList();

        EventTranslationResult translation = resolveTranslation(event.getEventId(), languageId);

        VenueResult venueResult = new VenueResult(venue.getVenueId(), venue.getName(), venue.getAddress());

        return new EventResult(event.getEventId(), event.getStartsAt(), venueResult, teams, translation);
    }

        private Integer resolveLanguageId(String languageCode) {
                Language language = languageRepository.findByCode(languageCode)
                .or(() -> languageRepository.findByCode("en"))
                .orElse(null);

                return language != null ? language.getLanguageId() : null;
        }

        private EventTranslationResult resolveTranslation(Long eventId, Integer languageId) {
                if (languageId == null) {
            return null;
        }

                return eventTranslationRepository.findByEventIdAndLanguageId(eventId, languageId)
                .map(t -> new EventTranslationResult(
                        t.getEventTranslationId(),
                        t.getLanguageId(),
                        t.getName(),
                        t.getDescription()))
                .orElse(null);
    }

        private String resolveSportName(Integer sportId, Integer languageId) {
                if (sportId == null) {
                        return null;
                }

                if (languageId != null) {
                        String localized = sportTranslationRepository.findBySportIdAndLanguageId(sportId, languageId)
                                        .map(t -> t.getName())
                                        .orElse(null);
                        if (localized != null) {
                                return localized;
                        }
                }

                Integer englishLanguageId = languageRepository.findByCode("en")
                                .map(Language::getLanguageId)
                                .orElse(null);
                if (englishLanguageId == null) {
                        return null;
                }

                return sportTranslationRepository.findBySportIdAndLanguageId(sportId, englishLanguageId)
                                .map(t -> t.getName())
                                .orElse(null);
        }
}
