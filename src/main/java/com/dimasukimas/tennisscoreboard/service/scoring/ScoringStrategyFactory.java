package com.dimasukimas.tennisscoreboard.service.scoring;

import com.dimasukimas.tennisscoreboard.enums.ScoringStrategyType;
import com.dimasukimas.tennisscoreboard.model.match.OngoingMatch;

public class ScoringStrategyFactory {

    private static final ScoringStrategy <OngoingMatch, Integer> DEFAULT_STRATEGY = new BestOfThreeScoringStrategy();

    public static ScoringStrategy<OngoingMatch, Integer> getStrategy(ScoringStrategyType type) {
        return DEFAULT_STRATEGY;
    }
}
