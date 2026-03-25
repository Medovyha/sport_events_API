package com.sport_events.api.application.usecase;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.application.dto.result.VenueResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Venue;
import com.sport_events.api.domain.repository.VenueRepository;

@ExtendWith(MockitoExtension.class)
class GetVenueUseCaseTest {

    @Mock
    private VenueRepository venueRepository;

    private GetVenueUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetVenueUseCase(venueRepository);
    }

    @Test
    void findById_returnsVenueResult() {
        when(venueRepository.findById(1)).thenReturn(Optional.of(new Venue(1, "Bernabéu", "Madrid")));

        VenueResult result = useCase.findById(1);

        assertThat(result.venueId()).isEqualTo(1);
        assertThat(result.name()).isEqualTo("Bernabéu");
        assertThat(result.address()).isEqualTo("Madrid");
    }

    @Test
    void findById_throwsWhenVenueNotFound() {
        when(venueRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.findById(99))
                .isInstanceOf(DomainException.class)
                .hasMessage("Venue not found: 99");
    }

    @Test
    void findAll_returnsAllVenues() {
        when(venueRepository.findAll()).thenReturn(List.of(
                new Venue(1, "Bernabéu", "Madrid"),
                new Venue(2, "Camp Nou", "Barcelona")));

        List<VenueResult> results = useCase.findAll();

        assertThat(results).hasSize(2);
        assertThat(results.get(0).name()).isEqualTo("Bernabéu");
        assertThat(results.get(1).name()).isEqualTo("Camp Nou");
    }

    @Test
    void findAll_returnsEmptyListWhenNoVenues() {
        when(venueRepository.findAll()).thenReturn(List.of());

        assertThat(useCase.findAll()).isEmpty();
    }
}
