package com.cohort22.data.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Student extends User{

    @ManyToMany(mappedBy = "students")
    private List<Game> games;
    private int score;

}
