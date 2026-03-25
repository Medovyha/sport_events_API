package com.sport_events.api.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport_events.api.application.usecase.GetVenueUseCase;
import com.sport_events.api.presentation.dto.VenueResponse;
import com.sport_events.api.presentation.mapper.VenueResponseMapper;

@RestController
@RequestMapping("/venues")
public class VenueController {

    private final GetVenueUseCase getVenueUseCase;

    public VenueController(GetVenueUseCase getVenueUseCase) {
        this.getVenueUseCase = getVenueUseCase;
    }

    @GetMapping
    public ResponseEntity<List<VenueResponse>> getAllVenues() {
        List<VenueResponse> venues = getVenueUseCase.findAll().stream()
                .map(VenueResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(venues);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueResponse> getVenue(@PathVariable Integer id) {
        return ResponseEntity.ok(VenueResponseMapper.toResponse(getVenueUseCase.findById(id)));
    }
}
