package com.dimasukimas.tennisscoreboard.repository;

import com.dimasukimas.tennisscoreboard.model.match.FinishedMatch;

public class MatchRepository extends BaseRepository<FinishedMatch>{

    private static MatchRepository instance;

    private MatchRepository(){

    }

    public static synchronized MatchRepository getInstance(){
        if (instance==null){
            instance = new MatchRepository();
        }
        return instance;
    }

}
