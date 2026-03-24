package com.sport_events.api.domain.model;

public class TeamPlayer {

	private Integer teamPlayerId;
	private Integer teamId;
	private Integer playerId;

	public TeamPlayer() {
	}

	public TeamPlayer(Integer teamPlayerId, Integer teamId, Integer playerId) {
		this.teamPlayerId = teamPlayerId;
		this.teamId = teamId;
		this.playerId = playerId;
	}

	public Integer getTeamPlayerId() {
		return teamPlayerId;
	}

	public void setTeamPlayerId(Integer teamPlayerId) {
		this.teamPlayerId = teamPlayerId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

}
