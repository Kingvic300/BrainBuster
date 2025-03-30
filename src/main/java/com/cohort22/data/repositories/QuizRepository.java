package com.cohort22.data.repositories;

import com.cohort22.data.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Quiz findQuizByTitle(String title);

    List<Quiz> findByTeacherId(Long id);
}
