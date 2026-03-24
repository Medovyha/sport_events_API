package com.sport_events.api.application.dto.result;

import java.time.LocalDate;

public record PlayerResult(
        Integer playerId,
        String firstName,
        String lastName,
        LocalDate dateOfBirth
) {
}
