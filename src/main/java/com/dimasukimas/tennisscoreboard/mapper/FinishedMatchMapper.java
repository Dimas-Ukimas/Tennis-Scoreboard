package com.dimasukimas.tennisscoreboard.mapper;

import com.dimasukimas.tennisscoreboard.dto.FinishedMatchResponseDto;
import com.dimasukimas.tennisscoreboard.model.entity.FinishedMatch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FinishedMatchMapper {

    FinishedMatchMapper INSTANCE = Mappers.getMapper(FinishedMatchMapper.class);

    @Mapping(source = "player1.name", target = "player1Name")
    @Mapping(source = "player2.name", target = "player2Name")
    @Mapping(source = "winner.name", target = "winnerName")
    FinishedMatchResponseDto toDto(FinishedMatch finishedMatch);
}
