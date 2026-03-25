package com.sport_events.api.presentation.exception;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sport_events.api.domain.exception.DomainException;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleDomainException_returnsNotFoundWithMessage() {
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
                handler.handleDomainException(new DomainException("Event not found: 1"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Event not found: 1");
    }
}
