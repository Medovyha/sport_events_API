package com.sport_events.api.domain.model;

public class EventTeam {

	private Integer eventTeamsId;
	private Long eventId;
	private Integer teamId;

	public EventTeam() {
	}

	public EventTeam(Integer eventTeamsId, Long eventId, Integer teamId) {
		this.eventTeamsId = eventTeamsId;
		this.eventId = eventId;
		this.teamId = teamId;
	}

	public Integer getEventTeamsId() {
		return eventTeamsId;
	}

	public void setEventTeamsId(Integer eventTeamsId) {
		this.eventTeamsId = eventTeamsId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

}
