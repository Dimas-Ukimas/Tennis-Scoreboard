package com.dimasukimas.tennisscoreboard.model.match;

import com.dimasukimas.tennisscoreboard.model.Player;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public abstract class Match {

    @ManyToOne
    protected Player player1;

    @ManyToOne
    protected Player player2;

    public Match(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    protected Match(){

    }

}
