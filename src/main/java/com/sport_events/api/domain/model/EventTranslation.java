package com.sport_events.api.domain.model;

public class EventTranslation {

	private Integer eventTranslationId;
	private Long eventId;
	private Integer languageId;
	private String name;
	private String description;

	public EventTranslation() {
	}

	public EventTranslation(Integer eventTranslationId, Long eventId, Integer languageId, String name, String description) {
		this.eventTranslationId = eventTranslationId;
		this.eventId = eventId;
		this.languageId = languageId;
		this.name = name;
		this.description = description;
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
