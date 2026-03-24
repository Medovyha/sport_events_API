package com.sport_events.api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "venues")
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

	public VenueJpaEntity() {
	}

	public Integer getVenueId() {
		return venueId;
	}

	public void setVenueId(Integer venueId) {
		this.venueId = venueId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
