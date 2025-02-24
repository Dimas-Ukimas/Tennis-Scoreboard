package com.dimasukimas.tennisscoreboard.model.common;

import com.dimasukimas.tennisscoreboard.enumeration.MatchState;
import com.dimasukimas.tennisscoreboard.enumeration.ScoringStrategyType;
import com.dimasukimas.tennisscoreboard.model.entity.Player;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OngoingMatch {
    private MatchState matchState;
    private ScoringStrategyType scoringStrategyType;

    private Player player1;
    private Player player2;

    private int player1Points;
    private int player2Points;

    private int player1Games;
    private int player2Games;

    private int player1Sets;
    private int player2Sets;

    public OngoingMatch(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.scoringStrategyType = ScoringStrategyType.BEST_OF_THREE;
        this.matchState = MatchState.NORMAL;
    }
}
