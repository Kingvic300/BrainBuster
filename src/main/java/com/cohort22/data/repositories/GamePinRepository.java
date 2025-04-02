package com.cohort22.data.repositories;

import com.cohort22.data.models.Game;
import com.cohort22.data.models.GamePin;
import com.cohort22.data.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GamePinRepository extends JpaRepository<GamePin, Long> {
    Optional<GamePin> findByPin(String gamePin);

    GamePin findByGame(Game game);
    Optional<GamePin> findByGameId(Long id);

    boolean existsByPin(String pin);
}
