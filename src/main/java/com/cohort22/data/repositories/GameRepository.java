package com.cohort22.data.repositories;

import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.Game;
import com.cohort22.data.models.Student;
import com.cohort22.data.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAllByStatus(GameStatus gameStatus);

    Optional<Game> findByGamePin_Pin(String gamePin);

    List<Game> findByStudentsId(Long id);

    Optional<Game> findByTeacher(Teacher teacher);
}
