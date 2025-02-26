package com.dimasukimas.tennisscoreboard.controller;

import com.dimasukimas.tennisscoreboard.dto.OngoingMatchResponseDto;
import com.dimasukimas.tennisscoreboard.service.MatchScoreCalculationService;
import com.dimasukimas.tennisscoreboard.service.OngoingMatchesService;
import com.dimasukimas.tennisscoreboard.util.DataValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreController extends HttpServlet {

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();
    private final MatchScoreCalculationService matchScoreCalculationService = MatchScoreCalculationService.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String matchUuid = request.getParameter("uuid");
        UUID validMatchUuid = DataValidationUtil.parseMatchUuid(matchUuid);

        OngoingMatchResponseDto matchScore = ongoingMatchesService.getMatchScore(validMatchUuid);

        request.setAttribute("match", matchScore);
        request.getRequestDispatcher("WEB-INF/match-score.jsp").forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long winnerId = DataValidationUtil.parsePlayerId(request.getParameter("playerId"));
        UUID validMatchUuid = DataValidationUtil.parseMatchUuid(request.getParameter("uuid"));

        OngoingMatchResponseDto updatedMatch = matchScoreCalculationService.processMatch(validMatchUuid, winnerId);

        request.setAttribute("match", updatedMatch);
        request.getRequestDispatcher("WEB-INF/match-score.jsp").forward(request, response);

    }


}
