package com.dimasukimas.tennisscoreboard.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Entity
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "players", schema = "public")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Setter
    @ToString.Include
    private String name;

    public Player(String name) {
        this.name = name;
    }
}


