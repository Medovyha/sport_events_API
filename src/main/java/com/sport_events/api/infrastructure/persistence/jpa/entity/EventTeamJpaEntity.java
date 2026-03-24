package com.sport_events.api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event_teams")
@Getter
@Setter
@NoArgsConstructor
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
}
