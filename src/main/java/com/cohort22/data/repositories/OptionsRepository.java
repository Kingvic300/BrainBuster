package com.cohort22.data.repositories;

import com.cohort22.data.models.Options;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OptionsRepository extends MongoRepository<Options, String> {
    Optional<Options> findOptionsByText(@NotNull(message = "text can't be null") @NotBlank(message = "text can't be blank") String text);

    List<Options> findByQuestionId(String id);
}