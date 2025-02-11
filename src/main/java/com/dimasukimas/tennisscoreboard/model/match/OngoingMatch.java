package com.dimasukimas.tennisscoreboard.model.match;

import com.dimasukimas.tennisscoreboard.enums.MatchState;
import com.dimasukimas.tennisscoreboard.enums.ScoringStrategyType;
import com.dimasukimas.tennisscoreboard.model.Player;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OngoingMatch extends Match {
    private MatchState matchState;
    private ScoringStrategyType scoringStrategyType;

    private int player1Points;
    private int player1Games;
    private int player1Sets;

    private int player2Points;
    private int player2Games;
    private int player2Sets;

    public OngoingMatch(Player player1, Player player2, ScoringStrategyType strategyType) {
        super(player1, player2);
        this.scoringStrategyType = strategyType;
        this.matchState = MatchState.NORMAL;
    }
}
