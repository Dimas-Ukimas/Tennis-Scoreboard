package com.dimasukimas.tennisscoreboard.model.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OngoingMatch extends Match {

    private int player1Sets;
    private int player2Sets;

    private int player1Games;
    private int player2Games;

    private int player1Points;
    private int player2Points;

    public void resetPoints(){
        player1Points = 0;
        player2Points = 0;
    }
}
