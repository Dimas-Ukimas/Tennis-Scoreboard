package com.dimasukimas.tennisscoreboard.service.scoring;

import com.dimasukimas.tennisscoreboard.model.common.MatchState;
import com.dimasukimas.tennisscoreboard.model.common.OngoingMatch;

public class BestOfThreeScoringStrategy extends BaseTennisScoringStrategy {

    protected int MIN_POINTS_TO_WIN_TIEBREAK;
    protected int GAMES_TO_START_TIEBREAK;
    protected int DEUCE_POINTS;

    @Override
    protected void initializeRulesParameters() {
        SETS_TO_WIN_MATCH = 2;
        GAMES_TO_WIN_SET = 6;
        POINTS_TO_WIN_GAME = 4;
        MIN_POINTS_TO_WIN_TIEBREAK = 7;
        GAMES_TO_START_TIEBREAK = 6;
        DEUCE_POINTS = 3;
    }

    @Override
    public void calculateScore(OngoingMatch match, Long winnerId) {
        updateMatchScore(match, winnerId);
        updateMatchState(match);
        setMatchWinnerIfFinished(match, winnerId);
    }

    protected void updateMatchScore(OngoingMatch match, Long winnerId) {
        MatchState currentMatchState = match.getMatchState();

        if (currentMatchState == MatchState.ADVANTAGE) {
            scorePointsByAdvantageRules(match, winnerId);
        } else {
            addPointToWinner(match, winnerId);
        }
        if (isGameWon(match)) {
            addGameToWinner(match, winnerId);
            resetPlayersPoints(match);
        }
        if (isSetWon(match)) {
            addSetToWinner(match, winnerId);
            resetPlayersGames(match);
        }
    }

    protected void updateMatchState(OngoingMatch match) {
        if (isMatchFinished(match)) {
            match.setMatchState(MatchState.FINISHED);
        } else if (isTieBreak(match)) {
            match.setMatchState(MatchState.TIEBREAK);
        } else if (isAdvantage(match)) {
            match.setMatchState(MatchState.ADVANTAGE);
        } else
            match.setMatchState(MatchState.NORMAL);
    }

    protected boolean isGameWon(OngoingMatch match) {
        MatchState matchState = match.getMatchState();
        int player1Points = match.getPlayer1Points();
        int player2Points = match.getPlayer2Points();

        boolean hasTwoOrMorePointsDifference = Math.abs(player1Points - player2Points) >= 2;
        boolean hasRequiredPointsToWinGame = player1Points == POINTS_TO_WIN_GAME || player2Points == POINTS_TO_WIN_GAME;
        boolean hasRequiredPointsToWinTiebreak = player1Points >= MIN_POINTS_TO_WIN_TIEBREAK || player2Points >= MIN_POINTS_TO_WIN_TIEBREAK;

        if (matchState == MatchState.NORMAL) {
            return hasRequiredPointsToWinGame && hasTwoOrMorePointsDifference;
        }
        if (matchState == MatchState.ADVANTAGE) {
            return hasTwoOrMorePointsDifference;
        }
        if (matchState == MatchState.TIEBREAK) {
            return hasRequiredPointsToWinTiebreak && hasTwoOrMorePointsDifference;
        }
        return false;
    }

    protected boolean isSetWon(OngoingMatch match) {
        int player1Games = match.getPlayer1Games();
        int player2Games = match.getPlayer2Games();
        int gamesDifference = Math.abs(player1Games - player2Games);

        boolean hasTwoOrMoreGamesDifference = gamesDifference >= 2;
        boolean hasOneGameDifference = gamesDifference == 1;
        boolean hasRequiredGamesToWinSet = player1Games == GAMES_TO_WIN_SET || player2Games == GAMES_TO_WIN_SET;

        boolean hasSetWinConditionWithoutTiebreak = hasTwoOrMoreGamesDifference && hasRequiredGamesToWinSet;
        boolean hasSetWinConditionAtTiebreak = hasOneGameDifference && match.getMatchState() == MatchState.TIEBREAK;

        return hasSetWinConditionAtTiebreak || hasSetWinConditionWithoutTiebreak;
    }

    protected boolean isMatchFinished(OngoingMatch match) {
        int player1Sets = match.getPlayer1Sets();
        int player2Sets = match.getPlayer2Sets();

        return player1Sets == SETS_TO_WIN_MATCH || player2Sets == SETS_TO_WIN_MATCH;
    }

    protected boolean isTieBreak(OngoingMatch match) {
        int player1Games = match.getPlayer1Games();
        int player2Games = match.getPlayer2Games();

        return player1Games == player2Games && player1Games == GAMES_TO_START_TIEBREAK;
    }

    protected boolean isAdvantage(OngoingMatch match) {
        int player1Points = match.getPlayer1Points();
        int player2Points = match.getPlayer2Points();

        boolean hasRequiredPointsToWinGame = player1Points == POINTS_TO_WIN_GAME || player2Points == POINTS_TO_WIN_GAME;
        boolean hasOnePointDifference = Math.abs(player1Points - player2Points) == 1;

        return !isTieBreak(match) && hasRequiredPointsToWinGame && hasOnePointDifference;
    }

    protected void scorePointsByAdvantageRules(OngoingMatch match, Long winnerId) {
        int winnerPoints = (match.getPlayer1().getId() == winnerId)
                ? match.getPlayer1Points()
                : match.getPlayer2Points();

        if (winnerPoints == DEUCE_POINTS) {
            removePointFromLoser(match, winnerId);
        } else {
            addPointToWinner(match, winnerId);
        }
    }
}
