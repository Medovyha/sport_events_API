package com.sport_events.api.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sport_events.api.application.usecase.GetEventUseCase;
import com.sport_events.api.application.usecase.GetPlayerUseCase;
import com.sport_events.api.application.usecase.GetSportUseCase;
import com.sport_events.api.application.usecase.GetTeamUseCase;
import com.sport_events.api.application.usecase.GetVenueUseCase;
import com.sport_events.api.domain.repository.EventPlayerRepository;
import com.sport_events.api.domain.repository.EventRepository;
import com.sport_events.api.domain.repository.EventTeamRepository;
import com.sport_events.api.domain.repository.EventTranslationRepository;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.PlayerRepository;
import com.sport_events.api.domain.repository.SportRepository;
import com.sport_events.api.domain.repository.SportTranslationRepository;
import com.sport_events.api.domain.repository.TeamPlayerRepository;
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

    @Bean
    public GetVenueUseCase getVenueUseCase(VenueRepository venueRepository) {
        return new GetVenueUseCase(venueRepository);
    }

    @Bean
    public GetPlayerUseCase getPlayerUseCase(
            PlayerRepository playerRepository,
            TeamPlayerRepository teamPlayerRepository,
            TeamRepository teamRepository,
            LanguageRepository languageRepository,
            SportTranslationRepository sportTranslationRepository) {
        return new GetPlayerUseCase(playerRepository, teamPlayerRepository,
                teamRepository, languageRepository, sportTranslationRepository);
    }

    @Bean
    public GetTeamUseCase getTeamUseCase(
            TeamRepository teamRepository,
            LanguageRepository languageRepository,
            SportTranslationRepository sportTranslationRepository) {
        return new GetTeamUseCase(teamRepository, languageRepository, sportTranslationRepository);
    }

    @Bean
    public GetSportUseCase getSportUseCase(
            SportRepository sportRepository,
            SportTranslationRepository sportTranslationRepository,
            LanguageRepository languageRepository) {
        return new GetSportUseCase(sportRepository, sportTranslationRepository, languageRepository);
    }
}
