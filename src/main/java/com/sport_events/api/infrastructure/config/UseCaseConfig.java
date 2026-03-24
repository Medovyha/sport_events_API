package com.sport_events.api.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sport_events.api.application.usecase.GetEventUseCase;
import com.sport_events.api.domain.repository.EventPlayerRepository;
import com.sport_events.api.domain.repository.EventRepository;
import com.sport_events.api.domain.repository.EventTeamRepository;
import com.sport_events.api.domain.repository.EventTranslationRepository;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.PlayerRepository;
import com.sport_events.api.domain.repository.SportTranslationRepository;
import com.sport_events.api.domain.repository.TeamRepository;
import com.sport_events.api.domain.repository.VenueRepository;

@Configuration
public class UseCaseConfig {

    @Bean
    public GetEventUseCase getEventUseCase(
            EventRepository eventRepository,
            VenueRepository venueRepository,
            EventTeamRepository eventTeamRepository,
            EventTranslationRepository eventTranslationRepository,
            EventPlayerRepository eventPlayerRepository,
            PlayerRepository playerRepository,
            LanguageRepository languageRepository,
            TeamRepository teamRepository,
            SportTranslationRepository sportTranslationRepository) {
        return new GetEventUseCase(eventRepository, venueRepository, eventTeamRepository,
                eventTranslationRepository, eventPlayerRepository, playerRepository, languageRepository,
                teamRepository, sportTranslationRepository);
    }
}
