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
    private Quiz quiz;

    @ManyToOne
    private Teacher teacher;
}
