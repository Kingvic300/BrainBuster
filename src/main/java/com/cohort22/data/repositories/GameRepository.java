package com.cohort22.data.repositories;

import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.Game;
import com.cohort22.data.models.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {
    List<Game> findAllByStatus(GameStatus gameStatus);
    Optional<Game> findByTeacherId(String teacherId);
    boolean existsByStudentIdsContaining(String id);
    List<Game> findByStudentIdsContainingAndStatus(String id, GameStatus gameStatus);
    Optional<Game> findByGamePinId(String gamePinId);

    Optional<Game> findByStudentIdsContaining(String id);
}