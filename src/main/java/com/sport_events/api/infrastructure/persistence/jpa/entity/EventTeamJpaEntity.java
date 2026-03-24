package com.sport_events.api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_teams")
public class EventTeamJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_teams_event_teams_id_seq")
	@SequenceGenerator(name = "event_teams_event_teams_id_seq", sequenceName = "event_teams_event_teams_id_seq", allocationSize = 1)
	@Column(name = "event_teams_id", nullable = false)
	private Integer eventTeamsId;

	@Column(name = "event_id", nullable = false)
	private Long eventId;

	@Column(name = "team_id", nullable = false)
	private Integer teamId;

	public EventTeamJpaEntity() {
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
