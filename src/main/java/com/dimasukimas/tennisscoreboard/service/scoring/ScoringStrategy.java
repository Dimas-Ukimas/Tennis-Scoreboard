package com.dimasukimas.tennisscoreboard.service.scoring;

public interface ScoringStrategy <T,I> {

    void calculateScore (T t, I i);

}
