package com.sport_events.api.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SportResponse(
        Integer sportId,
        String name
) {
}
