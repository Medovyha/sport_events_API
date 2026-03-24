package com.sport_events.api.application.dto.result;

import java.util.List;

public record EventTeamResult(
        Integer eventTeamsId,
        Integer teamId,
        String teamName,
        Integer sportId,
        String sportName,
        List<PlayerResult> players
) {
}
