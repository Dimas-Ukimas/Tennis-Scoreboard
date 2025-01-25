package com.dimasukimas.tennisscoreboard.model.match;

import com.dimasukimas.tennisscoreboard.model.Player;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "matches", schema = "public")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @ManyToOne
    private Player player1;

    @Setter
    @ManyToOne
    private Player player2;

    @Setter
    @ManyToOne
    private Player winner;

    public Match(OngoingMatch match){
        this.player1 = match.getPlayer1();
        this.player2 = match.getPlayer2();
        this.winner = match.getWinner();
    }

}
