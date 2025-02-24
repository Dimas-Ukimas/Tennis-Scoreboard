package com.dimasukimas.tennisscoreboard.service.scoring;

import com.dimasukimas.tennisscoreboard.model.common.OngoingMatch;

public abstract class BaseTennisScoringStrategy<M, L extends Number> implements ScoringStrategy<OngoingMatch,Long>{

    protected int SETS_TO_WIN_MATCH;
    protected int GAMES_TO_WIN_SET ;
    protected int POINTS_TO_WIN_GAME;

    public BaseTennisScoringStrategy() {
        initializeRulesParameters();
    }

    @Override
    public abstract void calculateScore(OngoingMatch match, Long winnerId);

    protected abstract void initializeRulesParameters();

    protected void addSetToWinner(OngoingMatch match, Long winnerId) {
        if (winnerId == match.getPlayer1().getId()) {
            match.setPlayer1Sets(match.getPlayer1Sets() + 1);
        } else
            match.setPlayer2Sets(match.getPlayer2Sets() + 1);
    }

    protected void addGameToWinner(OngoingMatch match, Long winnerId) {
        if (winnerId == match.getPlayer1().getId()) {
            match.setPlayer1Games(match.getPlayer1Games() + 1);
        } else
            match.setPlayer2Games(match.getPlayer2Games() + 1);
    }

    protected void addPointToWinner(OngoingMatch match, Long winnerId) {
        if (winnerId == match.getPlayer1().getId()) {
            match.setPlayer1Points(match.getPlayer1Points() + 1);
        } else
            match.setPlayer2Points(match.getPlayer2Points() + 1);
    }

    protected void removePointFromLoser(OngoingMatch match, Long winnerId) {
        if (winnerId == match.getPlayer1().getId()) {
            match.setPlayer2Points(match.getPlayer2Points() - 1);
        } else {
            match.setPlayer1Points(match.getPlayer1Points() - 1);
        }
    }

    protected void resetPlayersPoints(OngoingMatch match) {
        match.setPlayer1Points(0);
        match.setPlayer2Points(0);
    }

    protected void resetPlayersGames(OngoingMatch match) {
        match.setPlayer1Games(0);
        match.setPlayer2Games(0);
    }


}
