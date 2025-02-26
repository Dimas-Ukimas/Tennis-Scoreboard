package com.dimasukimas.tennisscoreboard.dto;

import java.util.List;

public record PaginatedMatchesResponseDto(
        List<FinishedMatchResponseDto> matches,
        int currentPage,
        int totalPages
) {
}
