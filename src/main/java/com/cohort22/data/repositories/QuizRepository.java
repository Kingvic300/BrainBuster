package com.cohort22.data.repositories;

import com.cohort22.data.models.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends MongoRepository<Quiz, String> {
    Optional<Quiz> findByTitle(String title);
    List<Quiz> findByTeacherId(String id);
}