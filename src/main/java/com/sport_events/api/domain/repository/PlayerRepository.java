package com.sport_events.api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.sport_events.api.domain.model.Player;

public interface PlayerRepository {

	Optional<Player> findById(Integer playerId);

	List<Player> findAll();

	Player save(Player player);

	void deleteById(Integer playerId);
}
