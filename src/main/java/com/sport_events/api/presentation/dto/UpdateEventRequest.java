package com.sport_events.api.presentation.dto;

import java.time.OffsetDateTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateEventRequest(
        @NotNull OffsetDateTime startsAt,
        @NotNull Integer venueId,
        @NotBlank String name,
        @NotBlank String description,
        List<@Valid EventTranslationRequest> translations
) {
}
