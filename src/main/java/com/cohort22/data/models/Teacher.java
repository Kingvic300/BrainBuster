package com.cohort22.data.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Teacher extends User {

    @OneToMany
    private List<Quiz> quizzes;

    @OneToOne
    private GamePin pin;
}
