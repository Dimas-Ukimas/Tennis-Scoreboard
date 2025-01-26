package com.dimasukimas.tennisscoreboard.model.match;

import com.dimasukimas.tennisscoreboard.service.scoring.ScoringStrategy;
import lombok.*;


@Getter
@Setter
public class OngoingMatch extends ScorableMatch {
    MatchState matchState;
    ScoringStrategy scoringStrategy;

    private int player1Sets;
    private int player2Sets;

    private int player1Games;
    private int player2Games;

    private int player1Points;
    private int player2Points;

    public OngoingMatch(ScoringStrategy strategy){
        scoringStrategy = strategy;
        matchState = MatchState.INITIAL;
    }

    public void updateScore(int winnerId){
        scoringStrategy.calculateScore(this, winnerId );
    }



}
