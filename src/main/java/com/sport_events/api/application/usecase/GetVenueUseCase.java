package com.sport_events.api.application.usecase;

import java.util.List;

import com.sport_events.api.application.dto.query.GetVenueQuery;
import com.sport_events.api.application.dto.query.GetVenuesQuery;
import com.sport_events.api.application.dto.result.VenueResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.repository.VenueRepository;

public class GetVenueUseCase {

    private final VenueRepository venueRepository;

    public GetVenueUseCase(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public VenueResult findById(Integer venueId) {
        return execute(new GetVenueQuery(venueId));
    }

    public List<VenueResult> findAll() {
        return execute(new GetVenuesQuery());
    }

    public VenueResult execute(GetVenueQuery query) {
        return venueRepository.findById(query.venueId())
                .map(v -> new VenueResult(v.getVenueId(), v.getName(), v.getAddress()))
                .orElseThrow(() -> new DomainException("Venue not found: " + query.venueId()));
    }

    public List<VenueResult> execute(GetVenuesQuery query) {
        return venueRepository.findAll().stream()
                .map(v -> new VenueResult(v.getVenueId(), v.getName(), v.getAddress()))
                .toList();
    }
}
