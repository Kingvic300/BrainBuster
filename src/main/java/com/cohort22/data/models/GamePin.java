package com.cohort22.data.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "gamepin")
public class GamePin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pin;

    @ManyToOne
    @JoinColumn(name = "game", nullable = false)
    private Game game;
}
