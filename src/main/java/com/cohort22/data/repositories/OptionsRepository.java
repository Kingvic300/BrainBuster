package com.cohort22.data.repositories;

import com.cohort22.data.models.Options;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OptionsRepository extends MongoRepository<Options, String> {
    Optional<Options> findByQuestionId(String questionId);
}