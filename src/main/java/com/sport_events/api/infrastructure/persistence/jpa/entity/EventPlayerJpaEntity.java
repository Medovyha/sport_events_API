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
@Table(name = "event_players")
@Getter
@Setter
@NoArgsConstructor
public class EventPlayerJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_players_event_players_id_seq")
	@SequenceGenerator(name = "event_players_event_players_id_seq", sequenceName = "event_players_event_players_id_seq", allocationSize = 1)
	@Column(name = "event_players_id", nullable = false)
	private Integer eventPlayersId;

	@Column(name = "event_team_id", nullable = false)
	private Integer eventTeamId;

	@Column(name = "player_id", nullable = false)
	private Integer playerId;
}
