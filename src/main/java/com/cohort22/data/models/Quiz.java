package com.cohort22.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "quizzes")
public class Quiz {
    @Id
    private String id;

    @Indexed(unique = true)
    private String title;

    private String teacherId;

    private List<String> questionIds;

    private String gameId;
}