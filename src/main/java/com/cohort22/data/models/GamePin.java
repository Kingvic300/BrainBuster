package com.cohort22.data.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class GamePin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pin;

    @OneToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}
