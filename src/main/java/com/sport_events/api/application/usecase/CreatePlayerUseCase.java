package com.sport_events.api.application.usecase;

import com.sport_events.api.application.dto.command.CreatePlayerCommand;
import com.sport_events.api.application.dto.result.PlayerResult;
import com.sport_events.api.domain.model.Player;
import com.sport_events.api.domain.repository.PlayerRepository;

public class CreatePlayerUseCase {

    private final PlayerRepository playerRepository;

    public CreatePlayerUseCase(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerResult execute(CreatePlayerCommand command) {
        Player player = new Player(null, command.firstName(), command.lastName(), command.dateOfBirth());
        Player saved = playerRepository.save(player);
        return new PlayerResult(saved.getPlayerId(), saved.getFirstName(), saved.getLastName(),
                saved.getDateOfBirth(), null);
    }
}
