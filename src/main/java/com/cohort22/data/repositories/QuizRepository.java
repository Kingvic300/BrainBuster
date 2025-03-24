package com.cohort22.data.repositories;

import com.cohort22.data.models.Options;
import com.cohort22.data.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
