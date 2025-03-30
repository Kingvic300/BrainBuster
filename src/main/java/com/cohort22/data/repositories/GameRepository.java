package com.cohort22.data.repositories;

import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.Game;
import com.cohort22.data.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByGamePin_Pin(String gamePin);
    List<Game> findAllByStatus(GameStatus gameStatus);
    List<Game> findByStudentContaining(Student student);
}
