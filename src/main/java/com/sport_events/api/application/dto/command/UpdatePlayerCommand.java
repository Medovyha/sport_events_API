package com.sport_events.api.application.dto.command;

import java.time.LocalDate;

public record UpdatePlayerCommand(
        Integer playerId,
        String firstName,
        String lastName,
        LocalDate dateOfBirth
) {
}
