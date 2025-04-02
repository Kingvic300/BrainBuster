package com.cohort22.data.models;

import com.cohort22.data.enums.GameStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePin_id")
    private Set<GamePin> gamePin;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    private GameStatus status;


    @OneToMany(mappedBy = "games",fetch = FetchType.EAGER)
    private List<Student> students;

    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Game game = (Game) object;
        return id != null && id.equals(game.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}



