package com.cohort22.data.repositories;

import com.cohort22.data.models.Teacher;
import com.cohort22.data.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {
    Optional<Teacher> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<Teacher> findByEmail(@NotNull(message = "email address can't be null") @NotBlank(message = "email address can't be blank") String email);

    Teacher findByEmailAndUsername(@NotNull(message = "email address can't be null") @NotBlank(message = "email address can't be blank") String email, @NotNull(message = "username can't be null") @NotBlank(message = "username can't be blank") String username);
}