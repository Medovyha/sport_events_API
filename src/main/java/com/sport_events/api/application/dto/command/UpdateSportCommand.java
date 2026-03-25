package com.sport_events.api.application.dto.command;

import java.util.List;

public record UpdateSportCommand(
        Integer sportId,
        List<SportTranslationCommand> translations
) {
}
