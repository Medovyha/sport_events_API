package com.sport_events.api.application.usecase;

import com.sport_events.api.application.dto.command.UpdatePlayerCommand;
import com.sport_events.api.application.dto.result.PlayerResult;
import com.sport_events.api.domain.exception.DomainException;
import com.sport_events.api.domain.model.Player;
import com.sport_events.api.domain.repository.PlayerRepository;

public class UpdatePlayerUseCase {

    private final PlayerRepository playerRepository;

    public UpdatePlayerUseCase(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerResult execute(UpdatePlayerCommand command) {
        playerRepository.findById(command.playerId())
                .orElseThrow(() -> new DomainException("Player not found: " + command.playerId()));
        Player player = new Player(command.playerId(), command.firstName(), command.lastName(), command.dateOfBirth());
        Player saved = playerRepository.save(player);
        return new PlayerResult(saved.getPlayerId(), saved.getFirstName(), saved.getLastName(), saved.getDateOfBirth(), null);
    }

    public void deleteById(Integer playerId) {
        playerRepository.findById(playerId)
                .orElseThrow(() -> new DomainException("Player not found: " + playerId));
        playerRepository.deleteById(playerId);
    }
}
