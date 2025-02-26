package com.dimasukimas.tennisscoreboard.mapper;

import com.dimasukimas.tennisscoreboard.dto.FinishedMatchResponseDto;
import com.dimasukimas.tennisscoreboard.dto.PaginatedMatchesResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PaginatedMatchesMapper {

    PaginatedMatchesMapper INSTANCE = Mappers.getMapper(PaginatedMatchesMapper.class);

    @Mapping(source = "matches", target = "matches")
    @Mapping(source = "currentPage", target = "currentPage")
    @Mapping(source = "totalPages", target = "totalPages")
    PaginatedMatchesResponseDto toPage(List<FinishedMatchResponseDto> matches, int currentPage, int totalPages);
}
