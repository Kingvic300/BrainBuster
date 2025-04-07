package com.cohort22.data.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "students")
public class Student extends User {
    private String newName;
    private int score;
    private String gamePin;
}