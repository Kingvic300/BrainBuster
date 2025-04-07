package com.cohort22.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "questions")
public class Question {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
    private String answer;

    private String quizId;

}