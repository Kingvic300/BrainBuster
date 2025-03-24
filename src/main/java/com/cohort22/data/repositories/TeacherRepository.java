package com.cohort22.data.repositories;

import com.cohort22.data.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long>       {
}
