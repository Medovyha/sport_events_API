package com.sport_events.api.application.usecase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sport_events.api.application.dto.command.CreateEventCommand;
import com.sport_events.api.application.dto.command.EventTranslationCommand;
import com.sport_events.api.application.dto.result.EventResult;
import com.sport_events.api.application.dto.result.EventTranslationResult;
import com.sport_events.api.application.dto.result.VenueResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Event;
import com.sport_events.api.domain.model.EventTranslation;
import com.sport_events.api.domain.model.Language;
import com.sport_events.api.domain.model.Venue;
import com.sport_events.api.domain.repository.EventRepository;
import com.sport_events.api.domain.repository.EventTranslationRepository;
import com.sport_events.api.domain.repository.LanguageRepository;
import com.sport_events.api.domain.repository.VenueRepository;

public class CreateEventUseCase {

	private final EventRepository eventRepository;
	private final EventTranslationRepository eventTranslationRepository;
	private final LanguageRepository languageRepository;
	private final VenueRepository venueRepository;

	public CreateEventUseCase(
			EventRepository eventRepository,
			EventTranslationRepository eventTranslationRepository,
			LanguageRepository languageRepository,
			VenueRepository venueRepository) {
		this.eventRepository = eventRepository;
		this.eventTranslationRepository = eventTranslationRepository;
		this.languageRepository = languageRepository;
		this.venueRepository = venueRepository;
	}

	public EventResult execute(CreateEventCommand command) {
		List<EventTranslationCommand> translations = command.translations();
		if (translations == null || translations.isEmpty()) {
			throw new DomainException("At least one translation is required");
		}
		validateUniqueLanguages(translations);

		Venue venue = venueRepository.findById(command.venueId())
				.orElseThrow(() -> new DomainException("Venue not found: " + command.venueId()));

		Event savedEvent = eventRepository.save(new Event(null, command.startsAt(), command.venueId()));

		EventTranslationResult responseTranslation = null;
		for (EventTranslationCommand translationCommand : translations) {
			Language language = languageRepository.findByCode(translationCommand.languageCode())
					.orElseThrow(() -> new DomainException("Language not found: " + translationCommand.languageCode()));

			EventTranslation savedTranslation = eventTranslationRepository.save(new EventTranslation(
					null,
					savedEvent.getEventId(),
					language.getLanguageId(),
					translationCommand.name(),
					translationCommand.description()));

			if (responseTranslation == null || "en".equals(translationCommand.languageCode())) {
				responseTranslation = new EventTranslationResult(
						savedTranslation.getEventTranslationId(),
						savedTranslation.getLanguageId(),
						savedTranslation.getName(),
						savedTranslation.getDescription());
			}
		}

		return new EventResult(
				savedEvent.getEventId(),
				savedEvent.getStartsAt(),
				new VenueResult(venue.getVenueId(), venue.getName(), venue.getAddress()),
				List.of(),
				responseTranslation);
	}

	private void validateUniqueLanguages(List<EventTranslationCommand> translations) {
		Set<String> languageCodes = new HashSet<>();
		for (EventTranslationCommand translation : translations) {
			String code = translation.languageCode();
			if (!languageCodes.add(code)) {
				throw new DomainException("Duplicate language in request: " + code);
			}
		}
	}
}
