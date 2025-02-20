package com.dimasukimas.tennisscoreboard.repository;

import com.dimasukimas.tennisscoreboard.model.match.FinishedMatch;
import com.dimasukimas.tennisscoreboard.util.HibernateUtil;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MatchRepository extends BaseRepository<FinishedMatch> {

    private final SessionFactory sessionFactory;

    private static final String FETCH_ALL_MATCHES_QUERY =
            "select fm from FinishedMatch fm " +
                    "JOIN FETCH fm.player1 p1 " +
                    "JOIN FETCH fm.player2 p2 " +
                    "JOIN FETCH fm.winner w ";

    private static final  String FILTER_BY_PLAYER_NAME_SUBQUERY =
            " WHERE p1.name LIKE :playerName " +
            " OR p2.name LIKE :playerName ";

    @Getter
    private static final MatchRepository instance = new MatchRepository();

    private MatchRepository() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<FinishedMatch> findAllPaginatedMatches(int offset, int pageSize) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FETCH_ALL_MATCHES_QUERY, FinishedMatch.class)
                    .setFirstResult(offset)
                    .setMaxResults(pageSize)
                    .getResultList();
        }
    }

    public List<FinishedMatch> findPlayerPaginatedMatches(int offset, int pageSize, String playerName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FETCH_ALL_MATCHES_QUERY +
                            FILTER_BY_PLAYER_NAME_SUBQUERY
                            , FinishedMatch.class)
                    .setParameter("playerName", "%" + playerName + "%")
                    .setFirstResult(offset)
                    .setMaxResults(pageSize)
                    .getResultList();
        }
    }

    public long countAllMatches() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FETCH_ALL_MATCHES_QUERY, FinishedMatch.class)
                    .getResultCount();
        }
    }

    public long countPlayerMatches(String playerName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FETCH_ALL_MATCHES_QUERY +
                            FILTER_BY_PLAYER_NAME_SUBQUERY
                            , FinishedMatch.class)
                    .setParameter("playerName", "%" + playerName + "%")
                    .getResultCount();
        }
    }
}


