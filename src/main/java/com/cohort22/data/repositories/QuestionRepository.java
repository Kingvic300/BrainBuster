package com.cohort22.data.repositories;

import com.cohort22.data.models.Options;
import com.cohort22.data.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
