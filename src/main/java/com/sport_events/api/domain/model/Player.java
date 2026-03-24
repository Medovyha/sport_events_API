package com.sport_events.api.domain.model;

import java.time.LocalDate;

public class Player {

	private Integer playerId;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

	public Player() {
	}

	public Player(Integer playerId, String firstName, String lastName, LocalDate dateOfBirth) {
		this.playerId = playerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
