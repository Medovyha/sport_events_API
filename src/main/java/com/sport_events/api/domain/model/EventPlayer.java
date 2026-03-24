package com.sport_events.api.domain.model;

public class EventPlayer {

	private Integer eventPlayersId;
	private Integer eventTeamId;
	private Integer playerId;

	public EventPlayer() {
	}

	public EventPlayer(Integer eventPlayersId, Integer eventTeamId, Integer playerId) {
		this.eventPlayersId = eventPlayersId;
		this.eventTeamId = eventTeamId;
		this.playerId = playerId;
	}

	public Integer getEventPlayersId() {
		return eventPlayersId;
	}

	public void setEventPlayersId(Integer eventPlayersId) {
		this.eventPlayersId = eventPlayersId;
	}

	public Integer getEventTeamId() {
		return eventTeamId;
	}

	public void setEventTeamId(Integer eventTeamId) {
		this.eventTeamId = eventTeamId;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

}
