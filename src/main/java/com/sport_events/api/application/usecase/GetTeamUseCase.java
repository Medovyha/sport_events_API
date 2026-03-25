package com.sport_events.api.application.usecase;

import java.util.List;

import com.sport_events.api.application.dto.result.TeamResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Language;
import com.sport_events.api.domain.model.Team;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.SportTranslationRepository;
import com.sport_events.api.domain.repository.TeamRepository;

public class GetTeamUseCase {

    private final TeamRepository teamRepository;
    private final LanguageRepository languageRepository;
    private final SportTranslationRepository sportTranslationRepository;

    public GetTeamUseCase(
            TeamRepository teamRepository,
            LanguageRepository languageRepository,
            SportTranslationRepository sportTranslationRepository) {
        this.teamRepository = teamRepository;
        this.languageRepository = languageRepository;
        this.sportTranslationRepository = sportTranslationRepository;
    }

    public TeamResult findById(Integer teamId, String languageCode) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new DomainException("Team not found: " + teamId));
        Integer languageId = resolveLanguageId(languageCode);
        String sportName = resolveSportName(team.getSportId(), languageId);
        return new TeamResult(team.getTeamId(), team.getName(), team.getSportId(), sportName);
    }

    public List<TeamResult> findAll(String languageCode) {
        Integer languageId = resolveLanguageId(languageCode);
        return teamRepository.findAll().stream()
                .map(t -> new TeamResult(t.getTeamId(), t.getName(), t.getSportId(),
                        resolveSportName(t.getSportId(), languageId)))
                .toList();
    }

    public List<TeamResult> findBySportId(Integer sportId, String languageCode) {
        Integer languageId = resolveLanguageId(languageCode);
        return teamRepository.findBySportId(sportId).stream()
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
            java.util.Optional<String> localized = sportTranslationRepository
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
