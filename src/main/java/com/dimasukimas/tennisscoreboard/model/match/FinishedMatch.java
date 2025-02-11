package com.dimasukimas.tennisscoreboard.model.match;

import com.dimasukimas.tennisscoreboard.model.Player;
import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
@Table(name = "Matches", schema = "public")
public class FinishedMatch extends Match{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public FinishedMatch(Player player1, Player player2, Player winner){
        super(player1, player2);
        this.winner = winner;
    }

    protected FinishedMatch(){

    }
}
