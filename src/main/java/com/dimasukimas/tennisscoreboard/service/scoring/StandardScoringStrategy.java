package com.dimasukimas.tennisscoreboard.service.scoring;

import com.dimasukimas.tennisscoreboard.enums.MatchState;
import com.dimasukimas.tennisscoreboard.model.match.OngoingMatch;

public class StandardScoringStrategy implements ScoringStrategy {

    private final int SETS_TO_WIN_MATCH = 2;
    private final int GAMES_TO_WIN_SET = 6;
    private final int POINTS_TO_WIN_GAME = 4;
    private final int TIEBREAK_THRESHOLD_POINTS_TO_WIN = 7;
    private final int TIEBREAK_THRESHOLD_GAMES = 6;
    private final int DEUCE_POINTS = 3;

    @Override
    public void calculateScore(OngoingMatch match, int winnerId) {
        setNextScore(match, winnerId);
        updateMatchStateAndWinner(match);
    }

    private void setNextScore(OngoingMatch match, int winnerId) {
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

    private void updateMatchStateAndWinner(OngoingMatch match) {
        if (isMatchFinished(match)) {
            match.setMatchState(MatchState.FINISHED);
            setMatchWinner(match);
        } else if (isTieBreak(match)) {
            match.setMatchState(MatchState.TIEBREAK);
        } else if (isDeuce(match)) {
            match.setMatchState(MatchState.DEUCE);
        } else if (isAdvantage(match)) {
            match.setMatchState(MatchState.ADVANTAGE);
        } else
            match.setMatchState(MatchState.NORMAL);
    }

    private void addSetToWinner(OngoingMatch match, int winnerId) {
        if (winnerId == match.getPlayer1().getId()) {
            match.setPlayer1Sets(match.getPlayer1Sets() + 1);
        } else
            match.setPlayer2Sets(match.getPlayer2Sets() + 1);
    }

    private void addGameToWinner(OngoingMatch match, int winnerId) {
        if (winnerId == match.getPlayer1().getId()) {
            match.setPlayer1Games(match.getPlayer1Games() + 1);
        } else
            match.setPlayer2Games(match.getPlayer2Games() + 1);
    }

    private void addPointToWinner(OngoingMatch match, int winnerId) {
        if (winnerId == match.getPlayer1().getId()) {
            match.setPlayer1Points(match.getPlayer1Points() + 1);
        } else
            match.setPlayer2Points(match.getPlayer2Points() + 1);
    }

    private boolean isGameWon(OngoingMatch match) {
        MatchState matchState = match.getMatchState();
        int player1Points = match.getPlayer1Points();
        int player2Points = match.getPlayer2Points();

        boolean hasTwoOrMorePointsDifference = Math.abs(player1Points - player2Points) >= 2;
        boolean hasRequiredPointsToWinGame = player1Points == POINTS_TO_WIN_GAME || player2Points == POINTS_TO_WIN_GAME;
        boolean hasRequiredPointsToWinTiebreak = player1Points >= TIEBREAK_THRESHOLD_POINTS_TO_WIN || player2Points >= TIEBREAK_THRESHOLD_POINTS_TO_WIN;

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

    private boolean isSetWon(OngoingMatch match) {
        int player1Games = match.getPlayer1Games();
        int player2Games = match.getPlayer2Games();
        int gamesDifference = Math.abs(player1Games - player2Games);

        boolean hasTwoOrMoreGamesDifference = gamesDifference >= 2;
        boolean hasOneGameDifference = gamesDifference == 1;
        boolean hasRequiredGamesToWinSet = player1Games == GAMES_TO_WIN_SET || player2Games == GAMES_TO_WIN_SET;

        boolean hasSetWinConditionWithoutTiebreak = hasTwoOrMoreGamesDifference && hasRequiredGamesToWinSet;
        boolean hasSetWinConditionAtTiebreak = hasOneGameDifference && match.getMatchState()==MatchState.TIEBREAK;

        return hasSetWinConditionAtTiebreak || hasSetWinConditionWithoutTiebreak;
    }

    private boolean isMatchFinished(OngoingMatch match) {
        int player1Sets = match.getPlayer1Sets();
        int player2Sets = match.getPlayer2Sets();

        return player1Sets == SETS_TO_WIN_MATCH || player2Sets == SETS_TO_WIN_MATCH;
    }

    private boolean isTieBreak(OngoingMatch match) {
        int player1Games = match.getPlayer1Games();
        int player2Games = match.getPlayer2Games();

        return player1Games == player2Games && player1Games == TIEBREAK_THRESHOLD_GAMES;
    }

    private boolean isAdvantage(OngoingMatch match) {
        int player1Points = match.getPlayer1Points();
        int player2Points = match.getPlayer2Points();

        boolean hasRequiredPointsToWinGame = player1Points == POINTS_TO_WIN_GAME || player2Points == POINTS_TO_WIN_GAME;
        boolean hasOnePointDifference = Math.abs(player1Points - player2Points) == 1;

        return hasRequiredPointsToWinGame && hasOnePointDifference;
    }

    private boolean isDeuce(OngoingMatch match) {
        int player1Points = match.getPlayer1Points();
        int player2Points = match.getPlayer2Points();

        return player1Points == player2Points && player1Points == DEUCE_POINTS;
    }

    private void scorePointsByAdvantageRules(OngoingMatch match, int winnerId) {
        int winnerPoints = (match.getPlayer1().getId() == winnerId)
                ? match.getPlayer1Points()
                : match.getPlayer2Points();

        if (winnerPoints == DEUCE_POINTS) {
            removePointFromLoser(match, winnerId);
        } else {
            addPointToWinner(match, winnerId);
        }
    }

    private void removePointFromLoser(OngoingMatch match, int winnerId) {
        if (match.getPlayer1().getId() == winnerId) {
            match.setPlayer2Points(match.getPlayer2Points() - 1);
        } else {
            match.setPlayer1Points(match.getPlayer1Points() - 1);
        }
    }

    private void setMatchWinner(OngoingMatch match) {
        if (isMatchFinished(match)) {
            if (match.getPlayer1Sets() == SETS_TO_WIN_MATCH) {
                match.setWinner(match.getPlayer1());
            } else {
                match.setWinner(match.getPlayer2());
            }
        }
    }

    private void resetPlayersPoints(OngoingMatch match) {
        match.setPlayer1Points(0);
        match.setPlayer2Points(0);
    }

    private void resetPlayersGames(OngoingMatch match) {
        match.setPlayer1Games(0);
        match.setPlayer2Games(0);
    }
}
