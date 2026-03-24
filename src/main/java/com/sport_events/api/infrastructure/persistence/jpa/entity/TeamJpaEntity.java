package com.sport_events.api.infrastructure.persistence.jpa.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
public class TeamJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teams_team_id_seq")
	@SequenceGenerator(name = "teams_team_id_seq", sequenceName = "teams_team_id_seq", allocationSize = 1)
	@Column(name = "team_id", nullable = false)
	private Integer teamId;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "sport_id", nullable = false)
	private SportJpaEntity sport;

	@OneToMany(mappedBy = "team")
	private Set<TeamPlayerJpaEntity> teamPlayers = new LinkedHashSet<>();

	@OneToMany(mappedBy = "team")
	private Set<EventTeamJpaEntity> eventTeams = new LinkedHashSet<>();
}
