package com.dimasukimas.tennisscoreboard.service;

import com.dimasukimas.tennisscoreboard.dto.OngoingMatchResponseDto;
import com.dimasukimas.tennisscoreboard.mapper.OngoingMatchMapper;
import com.dimasukimas.tennisscoreboard.model.common.MatchState;
import com.dimasukimas.tennisscoreboard.model.common.OngoingMatch;
import com.dimasukimas.tennisscoreboard.service.scoring.ScoringStrategy;
import com.dimasukimas.tennisscoreboard.service.scoring.ScoringStrategyFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchScoreCalculationService {
    private static final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();
    private static final FinishedMatchesPersistenceService finishedMatchesPersistenceService = FinishedMatchesPersistenceService.getInstance();
    private static final OngoingMatchMapper mapper = OngoingMatchMapper.INSTANCE;

    @Getter
    private final static MatchScoreCalculationService instance = new MatchScoreCalculationService();

    public OngoingMatchResponseDto processMatch(UUID matchUuid, Long winnerId) {
        OngoingMatch match = ongoingMatchesService.getMatch(matchUuid);

        updateMatchScore(winnerId, match);

        if (match.getMatchState() == MatchState.FINISHED) {
            ongoingMatchesService.deleteMatch(matchUuid);
            finishedMatchesPersistenceService.createAndPersistMatch(match);
        }
        return mapper.toDto(match, match.getMatchState());
    }

    private void updateMatchScore(Long winnerId, OngoingMatch match) {
        ScoringStrategy<OngoingMatch, Long> scoringStrategy = ScoringStrategyFactory.getStrategy(match.getScoringStrategyType());
        scoringStrategy.calculateScore(match, winnerId);
    }
}
