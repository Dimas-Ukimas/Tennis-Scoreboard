package com.dimasukimas.tennisscoreboard.service;

import com.dimasukimas.tennisscoreboard.dto.FinishedMatchResponseDto;
import com.dimasukimas.tennisscoreboard.dto.PaginatedMatchesResponseDto;
import com.dimasukimas.tennisscoreboard.mapper.FinishedMatchMapper;
import com.dimasukimas.tennisscoreboard.mapper.PaginatedMatchesMapper;
import com.dimasukimas.tennisscoreboard.model.common.OngoingMatch;
import com.dimasukimas.tennisscoreboard.model.entity.FinishedMatch;
import com.dimasukimas.tennisscoreboard.repository.MatchRepository;
import com.dimasukimas.tennisscoreboard.util.PaginationUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.LongSupplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FinishedMatchesPersistenceService {

    private static final MatchRepository matchRepository = MatchRepository.getInstance();
    private static final FinishedMatchMapper matchMapper = FinishedMatchMapper.INSTANCE;
    private static final PaginatedMatchesMapper pageMapper = PaginatedMatchesMapper.INSTANCE;

    @Getter
    private static final FinishedMatchesPersistenceService instance = new FinishedMatchesPersistenceService();

    public void createAndPersistMatch(OngoingMatch match) {
        FinishedMatch finishedMatch = new FinishedMatch(
                match.getPlayer1(),
                match.getPlayer2(),
                match.getWinner()
        );

        matchRepository.persist(finishedMatch);
    }

    public PaginatedMatchesResponseDto getAllPaginatedMatches(int pageNumber, int pageSize) {
        return getPaginatedMatches(pageNumber, pageSize,
                matchRepository::countAllMatches,
                matchRepository::findAllPaginatedMatches
        );
    }

    public PaginatedMatchesResponseDto getPlayerPaginatedMatches(int pageNumber, int pageSize, String playerName) {
        return getPaginatedMatches(pageNumber, pageSize,
                () -> matchRepository.countPlayerMatches(playerName),
                (offset, limit) -> matchRepository.findPlayerPaginatedMatches(offset, limit, playerName)
        );
    }

    private PaginatedMatchesResponseDto getPaginatedMatches(
            int pageNumber, int pageSize,
            LongSupplier totalCountSupplier,
            BiFunction<Integer, Integer, List<FinishedMatch>> matchesFetcher) {

        long totalMatchesCount = totalCountSupplier.getAsLong();
        int totalPages = PaginationUtil.getPagesCount(totalMatchesCount, pageSize);
        int currentPage = PaginationUtil.adjustPageNumber(pageNumber, totalPages);
        int offset = PaginationUtil.calculateOffset(currentPage, pageSize);
        List<FinishedMatch> finishedMatches = matchesFetcher.apply(offset, pageSize);


        return convertToPaginatedDto(finishedMatches, totalPages, currentPage);
    }

    private PaginatedMatchesResponseDto convertToPaginatedDto(List<FinishedMatch> finishedMatches, int totalPages, int currentPage) {
        List<FinishedMatchResponseDto> finishedMatchesDto = finishedMatches.stream()
                .map(matchMapper::toDto)
                .toList();

        return pageMapper.toPage(finishedMatchesDto, currentPage, totalPages);
    }
}
