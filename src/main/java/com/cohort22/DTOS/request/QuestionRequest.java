package com.cohort22.DTOS.request;

import com.cohort22.data.models.Options;
import com.cohort22.data.models.Quiz;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {
    private String name;
    private Long quizId;
    private Long optionsId;
    private String answer;
}
