package com.sport_events.api.presentation.controller;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.sport_events.api.application.dto.result.VenueResult;
import com.sport_events.api.application.usecase.CreateVenueUseCase;
import com.sport_events.api.application.usecase.GetVenueUseCase;
import com.sport_events.api.presentation.dto.CreateVenueRequest;
import com.sport_events.api.presentation.dto.VenueResponse;

@ExtendWith(MockitoExtension.class)
class VenueControllerTest {

    @Mock
    private GetVenueUseCase getVenueUseCase;
    @Mock
    private CreateVenueUseCase createVenueUseCase;

    @InjectMocks
    private VenueController controller;

    @Test
    void getAllVenues_returnsOkWithList() {
        when(getVenueUseCase.findAll()).thenReturn(List.of(
                new VenueResult(1, "Bernabéu", "Madrid"),
                new VenueResult(2, "Camp Nou", "Barcelona")));

        ResponseEntity<List<VenueResponse>> response = controller.getAllVenues();

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).name()).isEqualTo("Bernabéu");
        assertThat(response.getBody().get(1).name()).isEqualTo("Camp Nou");
    }

    @Test
    void getVenue_returnsOkWithVenueResponse() {
        when(getVenueUseCase.findById(1)).thenReturn(new VenueResult(1, "Bernabéu", "Madrid"));

        ResponseEntity<VenueResponse> response = controller.getVenue(1);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().venueId()).isEqualTo(1);
        assertThat(response.getBody().address()).isEqualTo("Madrid");
    }

    @Test
    void createVenue_returns201WithCreatedVenue() {
        when(createVenueUseCase.execute(any())).thenReturn(new VenueResult(3, "Wembley", "London"));

        ResponseEntity<VenueResponse> response = controller.createVenue(new CreateVenueRequest("Wembley", "London"));

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().name()).isEqualTo("Wembley");
        assertThat(response.getBody().address()).isEqualTo("London");
    }
}
