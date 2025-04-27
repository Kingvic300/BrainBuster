package com.cohort22.data.repositories;

import com.cohort22.data.enums.Roles;
import com.cohort22.data.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    List<User> getUsersByRole(Roles role);
    boolean existsByUsername(@NotNull(message = "username can't be null") @NotBlank(message = "username can't be blank") String username);
    User findByEmail(@NotNull(message = "email address can't be null") @NotBlank(message = "email address can't be blank") String email);
}