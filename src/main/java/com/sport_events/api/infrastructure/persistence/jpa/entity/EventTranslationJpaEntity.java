package com.sport_events.api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_translations")
public class EventTranslationJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_translations_event_translation_id_seq")
	@SequenceGenerator(name = "event_translations_event_translation_id_seq", sequenceName = "event_translations_event_translation_id_seq", allocationSize = 1)
	@Column(name = "event_translation_id", nullable = false)
	private Integer eventTranslationId;

	@Column(name = "event_id", nullable = false)
	private Long eventId;

	@Column(name = "language_id", nullable = false)
	private Integer languageId;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "description", nullable = false, columnDefinition = "text")
	private String description;

	public EventTranslationJpaEntity() {
	}

	public Integer getEventTranslationId() {
		return eventTranslationId;
	}

	public void setEventTranslationId(Integer eventTranslationId) {
		this.eventTranslationId = eventTranslationId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
