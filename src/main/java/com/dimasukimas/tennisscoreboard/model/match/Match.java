package com.dimasukimas.tennisscoreboard.model.match;

import com.dimasukimas.tennisscoreboard.model.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public abstract class Match {

    private Player player1;
    private Player player2;
    private Player winner;

    public Match(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }

}
