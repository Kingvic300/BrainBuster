package com.cohort22.data.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    @OneToMany
    private List<Options> options;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}
