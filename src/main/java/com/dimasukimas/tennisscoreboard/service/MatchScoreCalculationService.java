package com.dimasukimas.tennisscoreboard.service;

import com.dimasukimas.tennisscoreboard.dto.MatchScoreResponseDto;
import com.dimasukimas.tennisscoreboard.mapper.OngoingMatchMapper;
import com.dimasukimas.tennisscoreboard.model.match.FinishedMatch;
import com.dimasukimas.tennisscoreboard.enums.MatchState;
import com.dimasukimas.tennisscoreboard.model.match.OngoingMatch;

import java.util.Optional;
import java.util.UUID;

public class MatchScoreCalculationService {

    private static MatchScoreCalculationService instance;

    private final OngoingMatchesService ongoingMatchesService;
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    private MatchScoreCalculationService(OngoingMatchesService ongoingMatchesService, FinishedMatchesPersistenceService finishedMatchesPersistenceService) {
    this.ongoingMatchesService = ongoingMatchesService;
    this.finishedMatchesPersistenceService = finishedMatchesPersistenceService;
    }

    public static synchronized MatchScoreCalculationService getInstance(OngoingMatchesService ongoingMatchesService, FinishedMatchesPersistenceService finishedMatchesPersistenceService) {
        if (instance == null) {
            instance = new MatchScoreCalculationService(ongoingMatchesService, finishedMatchesPersistenceService);
        }
        return instance;
    }

    public MatchScoreResponseDto updateMatchScoreOrFinishMatch(UUID matchUuid, int winnerId) {
        Optional<OngoingMatch> ongoingMatch = ongoingMatchesService.getMatch(matchUuid);

        OngoingMatch match = ongoingMatch.orElseThrow();
        match.updateScore(winnerId);

        if (match.getMatchState() == MatchState.FINISHED) {
            ongoingMatchesService.deleteFinishedMatch(matchUuid);
            FinishedMatch finishedMatch = new FinishedMatch(match);
            finishedMatchesPersistenceService.persistMatch(finishedMatch);

        }

        return OngoingMatchMapper.INSTANCE.toMatchScoreDto(match, match.getMatchState());
    }


}
