package com.cohort22.data.repositories;

import com.cohort22.data.models.GamePin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamePinRepository extends JpaRepository<GamePin, Integer> {
}
