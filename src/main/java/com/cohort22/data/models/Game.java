package com.cohort22.data.models;

import com.cohort22.data.enums.GameStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Student> student;

    @OneToOne
    private Quiz quiz;

    @OneToOne
    private GamePin gamePin;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

}
