package com.sport_events.api.presentation.exception;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

    @Test
    void handleValidation_returnsBadRequestWithFieldErrors() throws Exception {
        var bindingResult = new BeanPropertyBindingResult(new Object(), "target");
        bindingResult.addError(new FieldError("target", "firstName", "must not be blank"));
        bindingResult.addError(new FieldError("target", "dateOfBirth", "must be in the past or present"));
        var ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<GlobalExceptionHandler.ValidationErrorResponse> response =
                handler.handleValidation(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().errors()).containsEntry("firstName", "must not be blank");
        assertThat(response.getBody().errors()).containsEntry("dateOfBirth", "must be in the past or present");
    }

    @Test
    void handleValidation_deduplicatesFieldErrors() throws Exception {
        var bindingResult = new BeanPropertyBindingResult(new Object(), "target");
        bindingResult.addError(new FieldError("target", "name", "must not be blank"));
        bindingResult.addError(new FieldError("target", "name", "size must be between 1 and 100"));
        var ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<GlobalExceptionHandler.ValidationErrorResponse> response =
                handler.handleValidation(ex);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().errors()).hasSize(1);
        assertThat(response.getBody().errors()).containsKey("name");
    }
}
