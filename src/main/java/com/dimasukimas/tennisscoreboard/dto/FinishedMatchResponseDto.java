package com.dimasukimas.tennisscoreboard.dto;

public record FinishedMatchResponseDto(
        String player1Name,
        String player2Name,
        String winnerName
        ) {
}
