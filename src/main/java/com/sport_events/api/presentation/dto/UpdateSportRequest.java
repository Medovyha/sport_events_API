package com.sport_events.api.presentation.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record UpdateSportRequest(
        @NotBlank String name,
        List<@Valid SportTranslationRequest> translations
) {
}
