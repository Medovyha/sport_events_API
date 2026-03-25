package com.sport_events.api.presentation.mapper;

import com.sport_events.api.application.dto.result.VenueResult;
import com.sport_events.api.presentation.dto.VenueResponse;

public class VenueResponseMapper {

    public static VenueResponse toResponse(VenueResult result) {
        return new VenueResponse(result.venueId(), result.name(), result.address());
    }
}
