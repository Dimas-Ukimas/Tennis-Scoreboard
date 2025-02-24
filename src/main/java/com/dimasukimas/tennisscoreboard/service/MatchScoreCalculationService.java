package com.dimasukimas.tennisscoreboard.service;

import com.dimasukimas.tennisscoreboard.dto.OngoingMatchResponseDto;
import com.dimasukimas.tennisscoreboard.enumeration.MatchState;
import com.dimasukimas.tennisscoreboard.mapper.OngoingMatchMapper;
import com.dimasukimas.tennisscoreboard.model.common.OngoingMatch;
import com.dimasukimas.tennisscoreboard.model.entity.Player;
import com.dimasukimas.tennisscoreboard.service.scoring.ScoringStrategy;
import com.dimasukimas.tennisscoreboard.service.scoring.ScoringStrategyFactory;
import lombok.Getter;

import java.util.UUID;

public class MatchScoreCalculationService {
    private final OngoingMatchesService ongoingMatchesService;
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService;
    private final OngoingMatchMapper mapper = OngoingMatchMapper.INSTANCE;

    private MatchScoreCalculationService() {
        this.ongoingMatchesService = OngoingMatchesService.getInstance();
        this.finishedMatchesPersistenceService = FinishedMatchesPersistenceService.getInstance();
    }

    @Getter
    private final static MatchScoreCalculationService instance = new MatchScoreCalculationService();

    public OngoingMatchResponseDto processMatch(UUID matchUuid, Long winnerId) {
        OngoingMatch match = ongoingMatchesService.getMatch(matchUuid);

        updateMatchScore(winnerId, match);

        if (match.getMatchState() == MatchState.FINISHED) {
            ongoingMatchesService.deleteMatch(matchUuid);
            Player matchWinner = defineMatchWinner(winnerId, match);
            finishedMatchesPersistenceService.createAndPersistMatch(matchWinner, match);

            return mapper.toDto(match, match.getMatchState(), winnerId);
        }
        return mapper.toDto(match, match.getMatchState());
    }

    private void updateMatchScore(Long winnerId, OngoingMatch match) {
        ScoringStrategy<OngoingMatch, Long> scoringStrategy = ScoringStrategyFactory.getStrategy(match.getScoringStrategyType());
        scoringStrategy.calculateScore(match, winnerId);
    }

    private Player defineMatchWinner(long winnerId, OngoingMatch match) {
        return winnerId == match.getPlayer1().getId()
                ? match.getPlayer1()
                : match.getPlayer2();
    }

}
