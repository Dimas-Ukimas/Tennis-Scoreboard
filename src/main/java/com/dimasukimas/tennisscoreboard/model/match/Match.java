package com.dimasukimas.tennisscoreboard.model.match;

import com.dimasukimas.tennisscoreboard.model.Player;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Match {

    private Player player1;
    private Player player2;
    private Player winner;

}
