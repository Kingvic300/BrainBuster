package com.cohort22.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Student extends User{

    @OneToMany
    private List<Game> gamingSession;
    private Long gameId;
    private int score;
}
