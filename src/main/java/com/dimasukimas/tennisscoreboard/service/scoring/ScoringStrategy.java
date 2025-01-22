package com.dimasukimas.tennisscoreboard.service.scoring;

import com.dimasukimas.tennisscoreboard.model.match.OngoingMatch;

public interface ScoringStrategy {

    void calculateScore (OngoingMatch match, int winnerId);

}
