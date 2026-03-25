package com.sport_events.api.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport_events.api.application.dto.command.CreateVenueCommand;
import com.sport_events.api.application.usecase.CreateVenueUseCase;
import com.sport_events.api.application.usecase.GetVenueUseCase;
import com.sport_events.api.presentation.dto.CreateVenueRequest;
import com.sport_events.api.presentation.dto.VenueResponse;
import com.sport_events.api.presentation.mapper.VenueResponseMapper;

@RestController
@RequestMapping("/venues")
public class VenueController {

    private final GetVenueUseCase getVenueUseCase;
    private final CreateVenueUseCase createVenueUseCase;

    public VenueController(GetVenueUseCase getVenueUseCase, CreateVenueUseCase createVenueUseCase) {
        this.getVenueUseCase = getVenueUseCase;
        this.createVenueUseCase = createVenueUseCase;
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

    @PostMapping
    public ResponseEntity<VenueResponse> createVenue(@Validated @RequestBody CreateVenueRequest request) {
        var result = createVenueUseCase.execute(new CreateVenueCommand(request.name(), request.address()));
        return ResponseEntity.status(HttpStatus.CREATED).body(VenueResponseMapper.toResponse(result));
    }
}
