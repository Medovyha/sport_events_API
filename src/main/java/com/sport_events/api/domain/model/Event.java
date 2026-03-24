package com.sport_events.api.domain.model;

import java.time.OffsetDateTime;

public class Event {

	private Long eventId;
	private OffsetDateTime startsAt;
	private Integer venueId;

	public Event() {
	}

	public Event(Long eventId, OffsetDateTime startsAt, Integer venueId) {
		this.eventId = eventId;
		this.startsAt = startsAt;
		this.venueId = venueId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public OffsetDateTime getStartsAt() {
		return startsAt;
	}

	public void setStartsAt(OffsetDateTime startsAt) {
		this.startsAt = startsAt;
	}

	public Integer getVenueId() {
		return venueId;
	}

	public void setVenueId(Integer venueId) {
		this.venueId = venueId;
	}
}
