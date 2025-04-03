package com.cohort22.data.models;

import com.cohort22.data.enums.GameStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Set;

@Data
@Document(collection = "games")
public class Game {
    @Id
    private String id;

    private Set<GamePin> gamePins;

    @DBRef
    private Quiz quiz;

    @DBRef
    private Teacher teacher;

    private GameStatus status;

    @DBRef
    private List<Student> students;

}