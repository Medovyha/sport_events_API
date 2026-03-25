package com.sport_events.api.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sport_events.api.domain.model.Player;
import com.sport_events.api.domain.repository.PlayerRepository;
import com.sport_events.api.infrastructure.persistence.jpa.repository.PlayerJpaRepository;
import com.sport_events.api.infrastructure.persistence.mapper.PlayerMapper;

@Repository
public class PlayerRepositoryAdapter implements PlayerRepository {

    private final PlayerJpaRepository jpaRepository;

    public PlayerRepositoryAdapter(PlayerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Player> findById(Integer playerId) {
        return jpaRepository.findById(playerId)
                .map(PlayerMapper::toDomain);
    }

    @Override
    public List<Player> findAll() {
        return jpaRepository.findAll().stream()
                .map(PlayerMapper::toDomain)
                .toList();
    }

    @Override
    public Player save(Player player) {
        var entity = PlayerMapper.toEntity(player);
        return PlayerMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void deleteById(Integer playerId) {
        jpaRepository.deleteById(playerId);
    }
}
