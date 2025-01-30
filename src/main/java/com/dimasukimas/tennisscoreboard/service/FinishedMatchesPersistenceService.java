package com.dimasukimas.tennisscoreboard.service;

import com.dimasukimas.tennisscoreboard.model.match.FinishedMatch;
import com.dimasukimas.tennisscoreboard.repository.MatchRepository;
import org.hibernate.SessionFactory;

public class FinishedMatchesPersistenceService {

    private final SessionFactory sessionFactory;
    private final MatchRepository matchRepository;

    private static FinishedMatchesPersistenceService instance;

    private FinishedMatchesPersistenceService(SessionFactory sessionFactory, MatchRepository matchRepository) {
        this.sessionFactory = sessionFactory;
        this.matchRepository = matchRepository;
    }

    public static synchronized FinishedMatchesPersistenceService getInstance(SessionFactory sessionFactory, MatchRepository matchRepository) {
        if (instance == null) {
            instance = new FinishedMatchesPersistenceService(sessionFactory, matchRepository);
        }
        return instance;
    }

    public void persistMatch(FinishedMatch finishedMatch) {
        matchRepository.persist(sessionFactory.openSession(), finishedMatch);
    }

}
