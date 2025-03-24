package com.cohort22.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}
