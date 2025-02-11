package com.dimasukimas.tennisscoreboard.mapper;

import com.dimasukimas.tennisscoreboard.model.dto.MatchScoreResponseDto;
import com.dimasukimas.tennisscoreboard.enums.MatchState;
import com.dimasukimas.tennisscoreboard.enums.TennisPointsView;
import com.dimasukimas.tennisscoreboard.model.match.OngoingMatch;
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
    @Mapping(source = "player1Games", target = "player1Games")
    @Mapping(source = "player1Sets", target = "player1Sets")
    @Mapping(source = "player2.id", target = "player2Id")
    @Mapping(source = "player2.name", target = "player2Name")
    @Mapping(source = "player2Points", target = "player2Points", qualifiedByName = "convertScore")
    @Mapping(source = "player2Games", target = "player2Games")
    @Mapping(source = "player2Sets", target = "player2Sets")
    @Mapping(source = "winner.id", target = "winnerId")
    MatchScoreResponseDto toMatchScoreDto(OngoingMatch match, @Context MatchState matchState);

    @Named("convertScore")
    default String convertScore(int score, @Context MatchState matchState) {
        if (matchState != MatchState.TIEBREAK) {
            if (score == 0) {
                return TennisPointsView.LOVE.getPoints();
            }
            if (score == 1) {
                return TennisPointsView.FIFTEEN.getPoints();
            }
            if (score == 2) {
                return TennisPointsView.THIRTY.getPoints();
            }
            if (score == 3) {
                return TennisPointsView.FORTY.getPoints();
            }
            if (matchState == MatchState.ADVANTAGE && score == 4) {
                return TennisPointsView.ADVANTAGE.getPoints();
            }
        }
        return String.valueOf(score);
    }
}
