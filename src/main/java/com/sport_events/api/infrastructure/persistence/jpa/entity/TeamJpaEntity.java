package com.sport_events.api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "teams")
public class TeamJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teams_team_id_seq")
	@SequenceGenerator(name = "teams_team_id_seq", sequenceName = "teams_team_id_seq", allocationSize = 1)
	@Column(name = "team_id", nullable = false)
	private Integer teamId;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "sport_id", nullable = false)
	private Integer sportId;

	public TeamJpaEntity() {
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSportId() {
		return sportId;
	}

	public void setSportId(Integer sportId) {
		this.sportId = sportId;
	}
}
