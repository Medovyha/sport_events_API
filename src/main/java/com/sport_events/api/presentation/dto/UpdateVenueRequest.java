package com.sport_events.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateVenueRequest(
        @NotBlank String name,
        @NotBlank String address
) {
}
