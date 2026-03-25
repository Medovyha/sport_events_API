package com.sport_events.api.application.usecase;

import com.sport_events.api.application.dto.command.CreateVenueCommand;
import com.sport_events.api.application.dto.result.VenueResult;
import com.sport_events.api.domain.model.Venue;
import com.sport_events.api.domain.repository.VenueRepository;

public class CreateVenueUseCase {

    private final VenueRepository venueRepository;

    public CreateVenueUseCase(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public VenueResult execute(CreateVenueCommand command) {
        Venue venue = new Venue(null, command.name(), command.address());
        Venue saved = venueRepository.save(venue);
        return new VenueResult(saved.getVenueId(), saved.getName(), saved.getAddress());
    }
}
