package com.cohort22.data.repositories;

import com.cohort22.data.models.Student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByUsername(String username);
    boolean existsByUsername(@NotNull(message = "username can't be null") @NotBlank(message = "username can't be blank") String username);
    Optional<Student> findByEmail(@NotNull(message = "email address can't be null") @NotBlank(message = "email address can't be blank") String email);
}