package com.sport_events.api.presentation.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record UpdatePlayerRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull @PastOrPresent LocalDate dateOfBirth
) {
}
