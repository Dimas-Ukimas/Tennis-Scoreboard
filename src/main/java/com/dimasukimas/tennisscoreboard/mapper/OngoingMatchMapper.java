package com.dimasukimas.tennisscoreboard.mapper;

import com.dimasukimas.tennisscoreboard.dto.OngoingMatchResponseDto;
import com.dimasukimas.tennisscoreboard.enumeration.MatchState;
import com.dimasukimas.tennisscoreboard.enumeration.TennisPoints;
import com.dimasukimas.tennisscoreboard.model.common.OngoingMatch;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OngoingMatchMapper {
    OngoingMatchMapper INSTANCE = Mappers.getMapper(OngoingMatchMapper.class);

    @Mapping(source = "player1.id", target = "player1Id")
    @Mapping(source = "player1.name", target = "player1Name")
    @Mapping(source = "player1Points", target = "player1Points", qualifiedByName = "convertScore")
    @Mapping(source = "player2.id", target = "player2Id")
    @Mapping(source = "player2.name", target = "player2Name")
    @Mapping(source = "player2Points", target = "player2Points", qualifiedByName = "convertScore")
    @Mapping(target = "winnerId", constant = "0")
    OngoingMatchResponseDto toDto(OngoingMatch match, @Context MatchState matchState);


    @Mapping(source = "match.player1.id", target = "player1Id")
    @Mapping(source = "match.player1.name", target = "player1Name")
    @Mapping(source = "match.player1Points", target = "player1Points", qualifiedByName = "convertScore")
    @Mapping(source = "match.player2.id", target = "player2Id")
    @Mapping(source = "match.player2.name", target = "player2Name")
    @Mapping(source = "match.player2Points", target = "player2Points", qualifiedByName = "convertScore")
    @Mapping(source = "winnerId", target = "winnerId")
    OngoingMatchResponseDto toDto(OngoingMatch match, @Context MatchState matchState, Long winnerId);


    @Named("convertScore")
    default String convertScore(int score, @Context MatchState matchState) {
        if (matchState != MatchState.TIEBREAK) {
            return TennisPoints.getViewByValue(score);
        }
        return String.valueOf(score);
    }
}
