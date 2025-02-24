package com.dimasukimas.tennisscoreboard.service;

import com.dimasukimas.tennisscoreboard.dto.OngoingMatchResponseDto;
import com.dimasukimas.tennisscoreboard.enumeration.MatchState;
import com.dimasukimas.tennisscoreboard.exception.NotFoundException;
import com.dimasukimas.tennisscoreboard.mapper.OngoingMatchMapper;
import com.dimasukimas.tennisscoreboard.model.common.OngoingMatch;
import com.dimasukimas.tennisscoreboard.model.entity.Player;
import com.dimasukimas.tennisscoreboard.repository.PlayerRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class OngoingMatchesService {
    private final Map<UUID, OngoingMatch> ongoingMatches = new ConcurrentHashMap<>();
    private final PlayerRepository playerRepository;
    private final OngoingMatchMapper mapper = OngoingMatchMapper.INSTANCE;

    private OngoingMatchesService() {
        this.playerRepository = PlayerRepository.getInstance();
    }

    @Getter
    private final static OngoingMatchesService instance = new OngoingMatchesService();

    public UUID createNewMatch(String player1Name, String player2Name) {
        UUID matchUuid = UUID.randomUUID();

        Player player1 = findOrCreatePlayer(player1Name);
        Player player2 = findOrCreatePlayer(player2Name);

        OngoingMatch match = new OngoingMatch(player1, player2);
        ongoingMatches.put(matchUuid, match);

        return matchUuid;
    }

    private Player findOrCreatePlayer(String name) {

        return playerRepository.findPlayerByName(name).orElseGet(() -> {
            Player newPlayer = new Player(name);
            return playerRepository.persist(newPlayer);
        });
    }

    protected void deleteMatch(UUID matchUuid) {
        ongoingMatches.remove(matchUuid);
    }

    protected OngoingMatch getMatch(UUID matchUuid) {
        return Optional.ofNullable(ongoingMatches.get(matchUuid))
                .orElseThrow(() -> {
                    log.warn("Match with UUID {} not found", matchUuid);
                    return new NotFoundException("Match " + matchUuid + " not found");
                });
    }

    public OngoingMatchResponseDto getMatchScore(UUID matchUuid) {
        MatchState matchState = ongoingMatches.get(matchUuid).getMatchState();

        return mapper.toDto(ongoingMatches.get(matchUuid), matchState);
    }
}
