package com.sport_events.api.infrastructure.persistence.jpa.entity;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
public class PlayerJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "players_player_id_seq")
	@SequenceGenerator(name = "players_player_id_seq", sequenceName = "players_player_id_seq", allocationSize = 1)
	@Column(name = "player_id", nullable = false)
	private Integer playerId;

	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;

	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;

	@OneToMany(mappedBy = "player")
	private Set<TeamPlayerJpaEntity> teamPlayers = new LinkedHashSet<>();

	@OneToMany(mappedBy = "player")
	private Set<EventPlayerJpaEntity> eventPlayers = new LinkedHashSet<>();
}
