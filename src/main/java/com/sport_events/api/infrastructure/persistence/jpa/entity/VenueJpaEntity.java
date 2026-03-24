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
@Table(name = "venues")
@Getter
@Setter
@NoArgsConstructor
public class VenueJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venues_venue_id_seq")
	@SequenceGenerator(name = "venues_venue_id_seq", sequenceName = "venues_venue_id_seq", allocationSize = 1)
	@Column(name = "venue_id", nullable = false)
	private Integer venueId;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "address", nullable = false, length = 255)
	private String address;

	@OneToMany(mappedBy = "venue")
	private Set<EventJpaEntity> events = new LinkedHashSet<>();
}
