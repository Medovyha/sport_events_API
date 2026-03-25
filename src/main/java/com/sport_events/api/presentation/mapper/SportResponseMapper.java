package com.sport_events.api.presentation.mapper;

import com.sport_events.api.application.dto.result.SportResult;
import com.sport_events.api.presentation.dto.SportResponse;

public class SportResponseMapper {

    public static SportResponse toResponse(SportResult result) {
        return new SportResponse(result.sportId(), result.name());
    }
}
