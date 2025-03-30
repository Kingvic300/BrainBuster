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

    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Game> games;

   }
