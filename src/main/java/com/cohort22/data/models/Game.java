package com.cohort22.data.models;

import com.cohort22.data.enums.GameStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "games")
public class Game {

    @Id
    private String id;
    private String gamePinId;
    private String quizId;
    private String teacherId;
    private GameStatus status;
    private List<String> studentIds;

}