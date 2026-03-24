package com.sport_events.api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "sports")
public class SportJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sports_sport_id_seq")
	@SequenceGenerator(name = "sports_sport_id_seq", sequenceName = "sports_sport_id_seq", allocationSize = 1)
	@Column(name = "sport_id", nullable = false)
	private Integer sportId;

	public SportJpaEntity() {
	}

	public Integer getSportId() {
		return sportId;
	}

	public void setSportId(Integer sportId) {
		this.sportId = sportId;
	}
}
