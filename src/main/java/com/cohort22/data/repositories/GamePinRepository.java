package com.cohort22.data.repositories;

import com.cohort22.data.models.Game;
import com.cohort22.data.models.GamePin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GamePinRepository extends MongoRepository<GamePin, String> {

    Optional<GamePin> findByPin(String gamePin);
    boolean existsByPin(String pin);

}