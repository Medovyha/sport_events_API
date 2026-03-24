package com.sport_events.api.domain.model;

public class Team {

	private Integer teamId;
	private String name;
	private Integer sportId;

	public Team() {
	}

	public Team(Integer teamId, String name, Integer sportId) {
		this.teamId = teamId;
		this.name = name;
		this.sportId = sportId;
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
