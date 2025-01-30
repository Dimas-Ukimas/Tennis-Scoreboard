package com.dimasukimas.tennisscoreboard.service;

import com.dimasukimas.tennisscoreboard.dto.MatchScoreResponseDto;
import com.dimasukimas.tennisscoreboard.enums.MatchState;
import com.dimasukimas.tennisscoreboard.mapper.OngoingMatchMapper;
import com.dimasukimas.tennisscoreboard.model.Player;
import com.dimasukimas.tennisscoreboard.model.match.OngoingMatch;
import com.dimasukimas.tennisscoreboard.repository.PlayerRepository;
import com.dimasukimas.tennisscoreboard.service.scoring.ScoringStrategy;
import com.dimasukimas.tennisscoreboard.service.scoring.StandardScoringStrategy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class OngoingMatchesService {

    private final Map<UUID, OngoingMatch> ongoingMatches = new HashMap<>();
    private final PlayerRepository playerRepository;
    private final SessionFactory sessionFactory;
    private static OngoingMatchesService instance;

    private OngoingMatchesService(PlayerRepository playerRepository, SessionFactory sessionFactory) {

        this.playerRepository = playerRepository;
        this.sessionFactory = sessionFactory;
    }

    public synchronized static OngoingMatchesService getInstance(PlayerRepository playerRepository, SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new OngoingMatchesService(playerRepository, sessionFactory);
        }
        return instance;
    }


    public UUID createNewMatch(String player1Name, String player2Name) {

        ScoringStrategy scoringStrategy = new StandardScoringStrategy();
        OngoingMatch match = new OngoingMatch(scoringStrategy);
        UUID matchUuid = UUID.randomUUID();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Player player1 = getOrCreatePlayer(session, player1Name);
            Player player2 = getOrCreatePlayer(session, player2Name);

            session.getTransaction().commit();

            match.setPlayer1(player1);
            match.setPlayer2(player2);
            ongoingMatches.put(matchUuid, match);

            return matchUuid;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create a new match", e);
        }
    }

    public void deleteFinishedMatch(UUID matchUuid) {
        ongoingMatches.remove(matchUuid);
    }

    private Player getOrCreatePlayer(Session session, String name) {

        return playerRepository.findPlayerByName(session, name).orElseGet(() -> {
            Player newPlayer = new Player(name);

            try {
                playerRepository.persist(session, newPlayer);
                return newPlayer;
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
    }

    public Optional<OngoingMatch> getMatch(UUID matchUuid) {

        return Optional.ofNullable(ongoingMatches.get(matchUuid));
    }


    public MatchScoreResponseDto getMatchScore(String uuid){

        UUID matchUuid = UUID.fromString(uuid);
        MatchState matchState = ongoingMatches.get(matchUuid).getMatchState();

        return OngoingMatchMapper.INSTANCE.toMatchScoreDto(ongoingMatches.get(matchUuid), matchState);
    }
}
