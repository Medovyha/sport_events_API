package com.sport_events.api.application.usecase;

import java.util.List;
import java.util.Optional;

import com.sport_events.api.application.dto.result.PlayerResult;
import com.sport_events.api.application.dto.result.TeamResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Language;
import com.sport_events.api.domain.model.TeamPlayer;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.PlayerRepository;
import com.sport_events.api.domain.repository.SportTranslationRepository;
import com.sport_events.api.domain.repository.TeamPlayerRepository;
import com.sport_events.api.domain.repository.TeamRepository;

public class GetPlayerUseCase {

    private final PlayerRepository playerRepository;
    private final TeamPlayerRepository teamPlayerRepository;
    private final TeamRepository teamRepository;
    private final LanguageRepository languageRepository;
    private final SportTranslationRepository sportTranslationRepository;

    public GetPlayerUseCase(
            PlayerRepository playerRepository,
            TeamPlayerRepository teamPlayerRepository,
            TeamRepository teamRepository,
            LanguageRepository languageRepository,
            SportTranslationRepository sportTranslationRepository) {
        this.playerRepository = playerRepository;
        this.teamPlayerRepository = teamPlayerRepository;
        this.teamRepository = teamRepository;
        this.languageRepository = languageRepository;
        this.sportTranslationRepository = sportTranslationRepository;
    }

    public PlayerResult findById(Integer playerId, String languageCode) {
        Integer languageId = resolveLanguageId(languageCode);
        return playerRepository.findById(playerId)
                .map(p -> new PlayerResult(p.getPlayerId(), p.getFirstName(), p.getLastName(), p.getDateOfBirth(),
                        resolvePlayerTeams(p.getPlayerId(), languageId)))
                .orElseThrow(() -> new DomainException("Player not found: " + playerId));
    }

    public List<PlayerResult> findAll(String languageCode) {
        Integer languageId = resolveLanguageId(languageCode);
        return playerRepository.findAll().stream()
                .map(p -> new PlayerResult(p.getPlayerId(), p.getFirstName(), p.getLastName(), p.getDateOfBirth(),
                        resolvePlayerTeams(p.getPlayerId(), languageId)))
                .toList();
    }

    public List<PlayerResult> findByTeamId(Integer teamId, String languageCode) {
        Integer languageId = resolveLanguageId(languageCode);
        return teamPlayerRepository.findByTeamId(teamId).stream()
                .map(TeamPlayer::getPlayerId)
                .map(pid -> playerRepository.findById(pid)
                        .orElseThrow(() -> new DomainException("Player not found: " + pid)))
                .map(p -> new PlayerResult(p.getPlayerId(), p.getFirstName(), p.getLastName(), p.getDateOfBirth(),
                        resolvePlayerTeams(p.getPlayerId(), languageId)))
                .toList();
    }

    private List<TeamResult> resolvePlayerTeams(Integer playerId, Integer languageId) {
        return teamPlayerRepository.findByPlayerId(playerId).stream()
                .map(tp -> teamRepository.findById(tp.getTeamId()).orElse(null))
                .filter(t -> t != null)
                .map(t -> new TeamResult(t.getTeamId(), t.getName(), t.getSportId(),
                        resolveSportName(t.getSportId(), languageId)))
                .toList();
    }

    private Integer resolveLanguageId(String languageCode) {
        Language language = languageRepository.findByCode(languageCode)
                .or(() -> languageRepository.findByCode("en"))
                .orElse(null);
        return language != null ? language.getLanguageId() : null;
    }

    private String resolveSportName(Integer sportId, Integer languageId) {
        if (sportId == null) {
            return null;
        }
        if (languageId != null) {
            Optional<String> localized = sportTranslationRepository
                    .findBySportIdAndLanguageId(sportId, languageId)
                    .map(st -> st.getName());
            if (localized.isPresent()) {
                return localized.get();
            }
        }
        return languageRepository.findByCode("en")
                .flatMap(en -> sportTranslationRepository.findBySportIdAndLanguageId(sportId, en.getLanguageId()))
                .map(st -> st.getName())
                .orElse(null);
    }
}

