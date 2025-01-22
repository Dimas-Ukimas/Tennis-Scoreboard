package com.dimasukimas.tennisscoreboard.model.match;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public abstract class ScorableMatch extends Match {

    private int player1Sets;
    private int player2Sets;

    private int player1Games;
    private int player2Games;

    private int player1Points;
    private int player2Points;

    public void addPointToWinner(int winnerId) {
        if (getPlayer1().getId() == winnerId) {
            player1Points++;
        } else player2Points++;
    }

    public void removePointFromLoser(int winnerId) {
        if (getPlayer1().getId() != winnerId) {
            player1Points--;
        } else player2Points--;
    }

    public void addGameToWinner(int winnerId) {
        if (getPlayer1().getId() == winnerId) {
            player1Games++;
        } else player2Games++;
    }

    public void addSetToWinner(int winnerId) {
        if (getPlayer1().getId() == winnerId) {
            player1Sets++;
        } else player2Sets++;
    }

    public int getWinnerPoints(int winnerId){
        if (getPlayer1().getId() == winnerId) {
            return player1Points;
        } else return player2Points;
    }

    public void resetPlayersPoints() {
        player1Points = 0;
        player2Points = 0;
    }
}
