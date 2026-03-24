package com.sport_events.api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "team_players")
public class TeamPlayerJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_players_team_player_id_seq")
	@SequenceGenerator(name = "team_players_team_player_id_seq", sequenceName = "team_players_team_player_id_seq", allocationSize = 1)
	@Column(name = "team_player_id", nullable = false)
	private Integer teamPlayerId;

	@Column(name = "team_id", nullable = false)
	private Integer teamId;

	@Column(name = "player_id", nullable = false)
	private Integer playerId;

	public TeamPlayerJpaEntity() {
	}

	public Integer getTeamPlayerId() {
		return teamPlayerId;
	}

	public void setTeamPlayerId(Integer teamPlayerId) {
		this.teamPlayerId = teamPlayerId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
}
