package com.cohort22.data.repositories;

import com.cohort22.data.models.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OptionsRepository extends JpaRepository<Options, Long> {
    Optional<Options> findByText(String text);
}
