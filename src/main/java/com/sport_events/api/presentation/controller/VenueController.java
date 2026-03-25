package com.sport_events.api.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport_events.api.application.dto.command.CreateVenueCommand;
import com.sport_events.api.application.dto.command.UpdateVenueCommand;
import com.sport_events.api.application.dto.query.GetVenueQuery;
import com.sport_events.api.application.dto.query.GetVenuesQuery;
import com.sport_events.api.application.usecase.CreateVenueUseCase;
import com.sport_events.api.application.usecase.GetVenueUseCase;
import com.sport_events.api.application.usecase.UpdateVenueUseCase;
import com.sport_events.api.presentation.dto.CreateVenueRequest;
import com.sport_events.api.presentation.dto.UpdateVenueRequest;
import com.sport_events.api.presentation.dto.VenueResponse;
import com.sport_events.api.presentation.mapper.VenueResponseMapper;

@RestController
@RequestMapping("/venues")
public class VenueController {

    private final GetVenueUseCase getVenueUseCase;
    private final CreateVenueUseCase createVenueUseCase;
    private final UpdateVenueUseCase updateVenueUseCase;

    public VenueController(
            GetVenueUseCase getVenueUseCase,
            CreateVenueUseCase createVenueUseCase,
            UpdateVenueUseCase updateVenueUseCase) {
        this.getVenueUseCase = getVenueUseCase;
        this.createVenueUseCase = createVenueUseCase;
        this.updateVenueUseCase = updateVenueUseCase;
    }

    @GetMapping
    public ResponseEntity<List<VenueResponse>> getAllVenues() {
        List<VenueResponse> venues = getVenueUseCase.execute(new GetVenuesQuery()).stream()
                .map(VenueResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(venues);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueResponse> getVenue(@PathVariable Integer id) {
        return ResponseEntity.ok(VenueResponseMapper.toResponse(getVenueUseCase.execute(new GetVenueQuery(id))));
    }

    @PostMapping
    public ResponseEntity<VenueResponse> createVenue(@Validated @RequestBody CreateVenueRequest request) {
        var result = createVenueUseCase.execute(new CreateVenueCommand(request.name(), request.address()));
        return ResponseEntity.status(HttpStatus.CREATED).body(VenueResponseMapper.toResponse(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueResponse> updateVenue(
            @PathVariable Integer id,
            @Validated @RequestBody UpdateVenueRequest request) {
        var result = updateVenueUseCase.execute(new UpdateVenueCommand(id, request.name(), request.address()));
        return ResponseEntity.ok(VenueResponseMapper.toResponse(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Integer id) {
        updateVenueUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
