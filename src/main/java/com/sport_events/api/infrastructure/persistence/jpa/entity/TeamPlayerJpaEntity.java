package com.sport_events.api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
	name = "team_players",
	uniqueConstraints = @UniqueConstraint(
		name = "team_players_team_id_player_id_uindex",
		columnNames = {"team_id", "player_id"}
	)
)
@Getter
@Setter
@NoArgsConstructor
public class TeamPlayerJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_players_team_player_id_seq")
	@SequenceGenerator(name = "team_players_team_player_id_seq", sequenceName = "team_players_team_player_id_seq", allocationSize = 1)
	@Column(name = "team_player_id", nullable = false)
	private Integer teamPlayerId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "team_id", nullable = false)
	private TeamJpaEntity team;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "player_id", nullable = false)
	private PlayerJpaEntity player;
}
