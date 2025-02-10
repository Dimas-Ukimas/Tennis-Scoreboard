package com.dimasukimas.tennisscoreboard.service.scoring;

public abstract class BaseTennisScoringStrategy <OngoingMatch,Integer> implements ScoringStrategy<OngoingMatch,Integer>{

    protected int SETS_TO_WIN_MATCH;
    protected int GAMES_TO_WIN_SET ;
    protected int POINTS_TO_WIN_GAME;

    public BaseTennisScoringStrategy() {
        initializeRulesParameters();
    }

    @Override
    public abstract void calculateScore(OngoingMatch match,Integer winnerId);

    protected abstract void initializeRulesParameters();

    protected void addSetToWinner(com.dimasukimas.tennisscoreboard.model.match.OngoingMatch match, int winnerId) {
        if (winnerId == match.getPlayer1().getId()) {
            match.setPlayer1Sets(match.getPlayer1Sets() + 1);
        } else
            match.setPlayer2Sets(match.getPlayer2Sets() + 1);
    }

    protected void addGameToWinner(com.dimasukimas.tennisscoreboard.model.match.OngoingMatch match, int winnerId) {
        if (winnerId == match.getPlayer1().getId()) {
            match.setPlayer1Games(match.getPlayer1Games() + 1);
        } else
            match.setPlayer2Games(match.getPlayer2Games() + 1);
    }

    protected void addPointToWinner(com.dimasukimas.tennisscoreboard.model.match.OngoingMatch match, int winnerId) {
        if (winnerId == match.getPlayer1().getId()) {
            match.setPlayer1Points(match.getPlayer1Points() + 1);
        } else
            match.setPlayer2Points(match.getPlayer2Points() + 1);
    }

    protected void removePointFromLoser(com.dimasukimas.tennisscoreboard.model.match.OngoingMatch match, int winnerId) {
        if (match.getPlayer1().getId() == winnerId) {
            match.setPlayer2Points(match.getPlayer2Points() - 1);
        } else {
            match.setPlayer1Points(match.getPlayer1Points() - 1);
        }
    }

    protected void resetPlayersPoints(com.dimasukimas.tennisscoreboard.model.match.OngoingMatch match) {
        match.setPlayer1Points(0);
        match.setPlayer2Points(0);
    }

    protected void resetPlayersGames(com.dimasukimas.tennisscoreboard.model.match.OngoingMatch match) {
        match.setPlayer1Games(0);
        match.setPlayer2Games(0);
    }


}
