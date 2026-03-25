package com.sport_events.api.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.application.dto.command.CreateVenueCommand;
import com.sport_events.api.domain.model.Venue;
import com.sport_events.api.domain.repository.VenueRepository;

@ExtendWith(MockitoExtension.class)
class CreateVenueUseCaseTest {

    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private CreateVenueUseCase createVenueUseCase;

    @Test
    void execute_savesVenueAndReturnsResult() {
        when(venueRepository.save(any())).thenReturn(new Venue(5, "Wembley", "London"));

        var result = createVenueUseCase.execute(new CreateVenueCommand("Wembley", "London"));

        assertThat(result.venueId()).isEqualTo(5);
        assertThat(result.name()).isEqualTo("Wembley");
        assertThat(result.address()).isEqualTo("London");
    }
}
