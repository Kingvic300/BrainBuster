package com.cohort22.data.repositories;

import com.cohort22.data.models.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionsRepository extends JpaRepository<Options, Long> {
}
