package com.sport_events.api.infrastructure.persistence.jpa.entity;

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
@Table(name = "sports")
@Getter
@Setter
@NoArgsConstructor
public class SportJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sports_sport_id_seq")
	@SequenceGenerator(name = "sports_sport_id_seq", sequenceName = "sports_sport_id_seq", allocationSize = 1)
	@Column(name = "sport_id", nullable = false)
	private Integer sportId;

	@OneToMany(mappedBy = "sport")
	private Set<TeamJpaEntity> teams = new LinkedHashSet<>();

	@OneToMany(mappedBy = "sport")
	private Set<SportTranslationJpaEntity> translations = new LinkedHashSet<>();
}
