package com.dimasukimas.tennisscoreboard.model.match;


import com.dimasukimas.tennisscoreboard.model.Player;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "Matches", schema = "public")
public class FinishedMatch extends Match{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @Setter
    private Player player1;

    @ManyToOne
    @Setter
    private Player player2;

    @ManyToOne
    @Setter
    private Player winner;

    public FinishedMatch(OngoingMatch match){
        this.player1 = match.getPlayer1();
        this.player2 = match.getPlayer2();
        this.winner = match.getWinner();
    }
}
