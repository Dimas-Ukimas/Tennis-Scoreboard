package com.dimasukimas.tennisscoreboard.service;

import com.dimasukimas.tennisscoreboard.mapper.FinishedMatchMapper;
import com.dimasukimas.tennisscoreboard.mapper.PaginatedMatchesMapper;
import com.dimasukimas.tennisscoreboard.model.Player;
import com.dimasukimas.tennisscoreboard.model.dto.FinishedMatchDto;
import com.dimasukimas.tennisscoreboard.model.dto.PaginatedMatchesDto;
import com.dimasukimas.tennisscoreboard.model.match.FinishedMatch;
import com.dimasukimas.tennisscoreboard.model.match.OngoingMatch;
import com.dimasukimas.tennisscoreboard.repository.MatchRepository;
import com.dimasukimas.tennisscoreboard.util.HibernateUtil;
import com.dimasukimas.tennisscoreboard.util.PaginationUtil;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.LongSupplier;

public class FinishedMatchesPersistenceService {

    private final SessionFactory sessionFactory;
    private final MatchRepository matchRepository;

    @Getter
    private static final FinishedMatchesPersistenceService instance = new FinishedMatchesPersistenceService();

    private FinishedMatchesPersistenceService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.matchRepository = MatchRepository.getInstance();
    }

    public void persistMatch(FinishedMatch finishedMatch) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            matchRepository.persist(session, finishedMatch);
            session.getTransaction().commit();
        }
    }

    public FinishedMatch createFinishedMatch(Player matchWinner, OngoingMatch match) {
        return new FinishedMatch(
                match.getPlayer1(),
                match.getPlayer2(),
                matchWinner
        );
    }

    public PaginatedMatchesDto getAllPaginatedMatches(int pageNumber, int pageSize) {
        return getPaginatedMatches(pageNumber, pageSize,
                matchRepository::countAllMatches,
                matchRepository::findAllPaginatedMatches
        );
    }

    public PaginatedMatchesDto getPlayerPaginatedMatches(int pageNumber, int pageSize, String playerName) {
        return getPaginatedMatches(pageNumber, pageSize,
                () -> matchRepository.countPlayerMatches(playerName),
                (offset, limit) -> matchRepository.findPlayerPaginatedMatches(offset, limit, playerName)
        );
    }

    private PaginatedMatchesDto getPaginatedMatches(
            int pageNumber, int pageSize,
            LongSupplier totalCountSupplier,
            BiFunction<Integer, Integer, List<FinishedMatch>> matchesFetcher) {

        int offset = (pageNumber - 1) * pageSize;
        long totalMatchesCount = totalCountSupplier.getAsLong();
        List<FinishedMatch> finishedMatches = matchesFetcher.apply(offset, pageSize);

        int totalPages = PaginationUtil.getPagesCount(totalMatchesCount, pageSize);
        int currentPage = PaginationUtil.adjustPageNumber(pageNumber, totalPages);

        return convertToPaginatedDto(finishedMatches, totalPages, currentPage);
    }

    private PaginatedMatchesDto convertToPaginatedDto(List<FinishedMatch> finishedMatches, int totalPages, int currentPage) {
        List<FinishedMatchDto> finishedMatchesDto = finishedMatches.stream()
                .map(FinishedMatchMapper.INSTANCE::toDto)
                .toList();

        return PaginatedMatchesMapper.INSTANCE.toPage(finishedMatchesDto, currentPage, totalPages);
    }
}
