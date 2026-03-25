package com.sport_events.api.application.usecase;

import com.sport_events.api.application.dto.command.UpdateVenueCommand;
import com.sport_events.api.application.dto.result.VenueResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Venue;
import com.sport_events.api.domain.repository.VenueRepository;

public class UpdateVenueUseCase {

    private final VenueRepository venueRepository;

    public UpdateVenueUseCase(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public VenueResult execute(UpdateVenueCommand command) {
        venueRepository.findById(command.venueId())
                .orElseThrow(() -> new DomainException("Venue not found: " + command.venueId()));
        Venue venue = new Venue(command.venueId(), command.name(), command.address());
        Venue saved = venueRepository.save(venue);
        return new VenueResult(saved.getVenueId(), saved.getName(), saved.getAddress());
    }

    public void deleteById(Integer venueId) {
        venueRepository.findById(venueId)
                .orElseThrow(() -> new DomainException("Venue not found: " + venueId));
        venueRepository.deleteById(venueId);
    }
}
