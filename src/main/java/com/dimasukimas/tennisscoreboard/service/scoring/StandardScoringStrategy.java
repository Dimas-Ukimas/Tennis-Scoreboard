package com.dimasukimas.tennisscoreboard.service.scoring;

import com.dimasukimas.tennisscoreboard.model.match.OngoingMatch;
import com.dimasukimas.tennisscoreboard.model.match.MatchState;

public class StandardScoringStrategy implements ScoringStrategy {

    @Override
    public void calculateScore(OngoingMatch match, int winnerId) {
        setNextScore(match, winnerId);
        updateMatchState(match);
    }

    private void updateMatchState(OngoingMatch match) {

        if (isMatchFinished(match.getPlayer1Sets(), match.getPlayer2Sets())) {
            match.setMatchState(MatchState.FINISHED);
        } else if (isTieBreak(match.getPlayer1Games(), match.getPlayer2Games())) {
            match.setMatchState(MatchState.TIEBREAK);
        } else if (isDeuce(match.getPlayer1Points(), match.getPlayer2Points())) {
            match.setMatchState(MatchState.DEUCE);
        } else if (isAdvantage(match.getPlayer1Points(), match.getPlayer2Points())) {
            match.setMatchState(MatchState.ADVANTAGE);
        } else
            match.setMatchState(MatchState.NORMAL);
    }

    private void setNextScore(OngoingMatch match, int winnerId) {
        MatchState currentMatchState = match.getMatchState();

        if (currentMatchState == MatchState.ADVANTAGE) {
            if (match.getWinnerPoints(winnerId) == 3) {
                match.removePointFromLoser(winnerId);
            }
        } else {
            match.addPointToWinner(winnerId);
        }

        if (isGameWon(match)) {
            match.addGameToWinner(winnerId);
            match.resetPlayersPoints();
        }
        if (isSetWon(match)) {
            match.addSetToWinner(winnerId);
        }
    }

    private boolean isGameWon(OngoingMatch match) {
        MatchState matchState = match.getMatchState();
        int player1Points = match.getPlayer1Points();
        int player2Points = match.getPlayer2Points();
        boolean isTwoPointsDifference = Math.abs(player1Points - player2Points) == 2;

        if (matchState == MatchState.INITIAL || matchState == MatchState.NORMAL) {
            return player1Points == 4 || player2Points == 4 && isTwoPointsDifference;
        }
        if (matchState == MatchState.ADVANTAGE) {
            return isTwoPointsDifference;
        }
        if (matchState == MatchState.TIEBREAK) {
            return player1Points >= 7 || player2Points >= 7 && isTwoPointsDifference;
        }
        return false;
    }

    private boolean isSetWon(OngoingMatch match) {
        MatchState matchState = match.getMatchState();
        int player1Games = match.getPlayer1Games();
        int player2Games = match.getPlayer2Games();

        boolean isTwoGameDifferenceAtSixGames = player1Games == 6 || player2Games == 6 && Math.abs(player1Games - player2Games) == 2;
        boolean isOneGameDifferenceAtTieBreak = Math.abs(player1Games - player2Games) == 1 && matchState == MatchState.TIEBREAK;

        return isOneGameDifferenceAtTieBreak || isTwoGameDifferenceAtSixGames;
    }

    public boolean isMatchFinished(int player1Sets, int player2Sets) {

        return player1Sets == 2 || player2Sets == 2;
    }

    private boolean isTieBreak(int player1Games, int player2Games) {

        return player1Games == player2Games && player1Games == 6;
    }

    private boolean isAdvantage(int player1Points, int player2Points) {
        boolean isFourPointsScore = player1Points == 4 || player2Points == 4;
        boolean isOnePointDifference = Math.abs(player1Points - player2Points) == 1;

        return isFourPointsScore && isOnePointDifference;
    }

    private boolean isDeuce(int player1Points, int player2Points) {
        return player1Points == player2Points && player1Points == 3;
    }

}
