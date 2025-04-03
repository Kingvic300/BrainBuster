package com.cohort22.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "options")
public class Options {
    @Id
    private String id;

    private String text;
    private Boolean isCorrect;

    private String questionId;
}