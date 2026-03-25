package com.sport_events.api.application.dto.command;

import java.time.LocalDate;

public record CreatePlayerCommand(
        String firstName,
        String lastName,
        LocalDate dateOfBirth
) {
}
