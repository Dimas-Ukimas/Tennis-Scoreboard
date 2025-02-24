package com.dimasukimas.tennisscoreboard.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "Matches", schema = "public")
public class FinishedMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "player1_id", nullable = false, foreignKey = @ForeignKey(name = "player1_id", foreignKeyDefinition = "ON DELETE CASCADE"))
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player2_id", nullable = false, foreignKey = @ForeignKey(name = "player2_id", foreignKeyDefinition = "ON DELETE CASCADE"))
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "winner_id", nullable = false, foreignKey = @ForeignKey(name = "winner_id", foreignKeyDefinition = "ON DELETE CASCADE"))
    private Player winner;

    public FinishedMatch(Player player1, Player player2, Player winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }

}
