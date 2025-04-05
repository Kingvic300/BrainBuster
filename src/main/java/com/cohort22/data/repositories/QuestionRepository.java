package com.cohort22.data.repositories;

import com.cohort22.data.models.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByQuizId(String quizId);

    Optional<Question> findByName(String name);
}