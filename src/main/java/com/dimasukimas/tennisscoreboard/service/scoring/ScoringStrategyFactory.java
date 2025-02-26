package com.dimasukimas.tennisscoreboard.service.scoring;

import com.dimasukimas.tennisscoreboard.model.common.OngoingMatch;

public class ScoringStrategyFactory {

    private static final ScoringStrategy <OngoingMatch, Long> DEFAULT_STRATEGY = new BestOfThreeScoringStrategy();

    public static ScoringStrategy<OngoingMatch, Long> getStrategy(ScoringStrategyType type) {
        return DEFAULT_STRATEGY;
    }
}
