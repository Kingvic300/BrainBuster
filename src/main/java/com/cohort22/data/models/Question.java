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
    private String text;

    @ManyToOne
    private Quiz quiz;

    @OneToMany
    private List<Options> options;

    private String answer;
    private Long quidId;
}
