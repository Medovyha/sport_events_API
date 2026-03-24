package com.sport_events.api.domain.model;

public class Venue {

	private Integer venueId;
	private String name;
	private String address;

	public Venue() {
	}

	public Venue(Integer venueId, String name, String address) {
		this.venueId = venueId;
		this.name = name;
		this.address = address;
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
