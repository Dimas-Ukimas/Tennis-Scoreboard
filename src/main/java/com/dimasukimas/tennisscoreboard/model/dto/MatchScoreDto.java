package com.dimasukimas.tennisscoreboard.model.dto;

public record MatchScoreResponseDto(
    int player1Id,
    String player1Name,
    String player1Points,
    String player1Games,
    String player1Sets,
    int player2Id,
    String player2Name,
    String player2Points,
    String player2Games,
    String player2Sets,
    int winnerId
){}
