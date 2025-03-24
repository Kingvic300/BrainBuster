package com.cohort22.data.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer;
    private String text;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;


}
