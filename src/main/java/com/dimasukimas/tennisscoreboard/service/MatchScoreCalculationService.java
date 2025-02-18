package com.dimasukimas.tennisscoreboard.service;

import com.dimasukimas.tennisscoreboard.enums.MatchState;
import com.dimasukimas.tennisscoreboard.mapper.OngoingMatchMapper;
import com.dimasukimas.tennisscoreboard.model.Player;
import com.dimasukimas.tennisscoreboard.model.dto.MatchScoreResponseDto;
import com.dimasukimas.tennisscoreboard.model.match.FinishedMatch;
import com.dimasukimas.tennisscoreboard.model.match.OngoingMatch;
import com.dimasukimas.tennisscoreboard.service.scoring.ScoringStrategy;
import com.dimasukimas.tennisscoreboard.service.scoring.ScoringStrategyFactory;
import lombok.Getter;

import java.util.UUID;

public class MatchScoreCalculationService {
    private final OngoingMatchesService ongoingMatchesService;
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    private MatchScoreCalculationService() {
        this.ongoingMatchesService = OngoingMatchesService.getInstance();
        this.finishedMatchesPersistenceService = FinishedMatchesPersistenceService.getInstance();
    }

    @Getter
    private final static MatchScoreCalculationService instance = new MatchScoreCalculationService();

    public MatchScoreResponseDto processMatch(UUID matchUuid, int winnerId) {
        OngoingMatch match = ongoingMatchesService.getMatch(matchUuid);

        updateMatchScore(winnerId, match);

        if (match.getMatchState() == MatchState.FINISHED) {
            ongoingMatchesService.deleteMatch(matchUuid);
            saveFinishedMatch(winnerId, match);

            return OngoingMatchMapper.INSTANCE.toDto(match, match.getMatchState(), winnerId);
        }

        return OngoingMatchMapper.INSTANCE.toDto(match, match.getMatchState());
    }

    private void updateMatchScore(int winnerId, OngoingMatch match) {
        ScoringStrategy<OngoingMatch, Integer> scoringStrategy = ScoringStrategyFactory.getStrategy(match.getScoringStrategyType());
        scoringStrategy.calculateScore(match, winnerId);
    }

    private void saveFinishedMatch(int winnerId, OngoingMatch match) {
            Player matchWinner = winnerId == match.getPlayer1().getId()
                    ? match.getPlayer1()
                    : match.getPlayer2();

            FinishedMatch finishedMatch = new FinishedMatch(
                    match.getPlayer1(),
                    match.getPlayer2(),
                    matchWinner
            );
            finishedMatchesPersistenceService.persistMatch(finishedMatch);
    }

}
