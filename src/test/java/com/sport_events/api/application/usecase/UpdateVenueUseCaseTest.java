package com.sport_events.api.application.usecase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sport_events.api.application.dto.command.UpdateVenueCommand;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Venue;
import com.sport_events.api.domain.repository.VenueRepository;

@ExtendWith(MockitoExtension.class)
class UpdateVenueUseCaseTest {

    @Mock
    private VenueRepository venueRepository;

    private UpdateVenueUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateVenueUseCase(venueRepository);
    }

    @Test
    void execute_updatesVenueAndReturnsResult() {
        when(venueRepository.findById(1)).thenReturn(Optional.of(new Venue(1, "Old", "Addr")));
        when(venueRepository.save(any())).thenReturn(new Venue(1, "New", "Address"));

        var result = useCase.execute(new UpdateVenueCommand(1, "New", "Address"));

        assertThat(result.venueId()).isEqualTo(1);
        assertThat(result.name()).isEqualTo("New");
    }

    @Test
    void execute_throwsWhenVenueNotFound() {
        when(venueRepository.findById(11)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new UpdateVenueCommand(11, "X", "Y")))
                .isInstanceOf(DomainException.class)
                .hasMessage("Venue not found: 11");
    }

    @Test
    void deleteById_deletesWhenVenueExists() {
        when(venueRepository.findById(1)).thenReturn(Optional.of(new Venue(1, "Old", "Addr")));

        useCase.deleteById(1);

        verify(venueRepository).deleteById(1);
    }

    @Test
    void deleteById_throwsWhenVenueNotFound() {
        when(venueRepository.findById(22)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.deleteById(22))
                .isInstanceOf(DomainException.class)
                .hasMessage("Venue not found: 22");
    }
}
